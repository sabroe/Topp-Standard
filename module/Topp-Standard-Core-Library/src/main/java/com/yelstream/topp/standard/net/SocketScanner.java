/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.net;

import com.yelstream.topp.standard.io.Printable;
import com.yelstream.topp.standard.util.concurrent.CompletableFutures;
import com.yelstream.topp.standard.util.concurrent.ManagedExecutor;
import com.yelstream.topp.standard.util.function.ex.SupplierWithException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Scans and tests for occupied and available sockets within a range of ports.
 * <p>
 *     Note that this has been setup to be able to scan repeatedly.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-05
 */
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor
@Slf4j
public class SocketScanner {
    /**
     * Suppliers of internet addresses.
     */
    @lombok.Singular
    private final List<SupplierWithException<InetAddress,IOException>> addressSuppliers;

    /**
     * Supplier of port range to scan.
     */
    @lombok.Builder.Default
    private final Supplier<IntStream> ports=()->IntStream.range(0,65536);

    /**
     * Connectivity timeout per individual port.
     */
    @lombok.Builder.Default
    private final Duration timeout=Duration.ofMillis(2000);

    /**
     * Source of executor.
     * In normal situations, this should be left at its default.
     */
    @lombok.Builder.Default
    private final Supplier<ManagedExecutor> executorSupplier=()->ManagedExecutor.of(Executors.newVirtualThreadPerTaskExecutor());

    /**
     * Method used to probe each port.
     */
    public enum Probe {
        /**
         * Connects a socket to the port; the port is occupied if the connect succeeds.
         * <p>
         *     This works for any address, local or remote.
         *     Note that on Windows, a connect to a closed port takes about 2 seconds to be refused.
         * </p>
         */
        CONNECT,

        /**
         * Binds a server-socket to the port; the port is occupied if the bind fails.
         * <p>
         *     This works for local addresses only.
         *     It is fast and does not use ephemeral ports.
         * </p>
         */
        BIND
    }

    /**
     * Probe method per port.
     */
    @lombok.Builder.Default
    private final Probe probe=Probe.CONNECT;

    /**
     * Maximum number of probes in flight at the same time.
     * <p>
     *     This keeps a scan from exhausting the ephemeral ports of the operating system.
     * </p>
     */
    @lombok.Builder.Default
    private final int maxConcurrency=1024;

    /**
     * Creates an executor that keeps at most {@link #maxConcurrency} commands in flight at the same time.
     * Submission blocks until a permit is free.
     * @param executor Underlying executor.
     * @return Limited executor.
     */
    private Executor createLimitedExecutor(Executor executor) {
        Semaphore permits=new Semaphore(maxConcurrency);
        return command->{
            permits.acquireUninterruptibly();
            try {
                executor.execute(()->{
                    try {
                        command.run();
                    } finally {
                        permits.release();
                    }
                });
            } catch (RuntimeException ex) {
                permits.release();
                throw ex;
            }
        };
    }

    /**
     * Probes a single port.
     * @param address Address.
     * @param port Port.
     * @param executor Executor.
     * @return Port if occupied, otherwise {@code null}.
     */
    private CompletableFuture<Integer> probePort(InetAddress address,
                                                 int port,
                                                 Executor executor) {
        InetSocketAddress endpoint=new InetSocketAddress(address,port);
        return switch (probe) {
            case CONNECT ->
                Sockets.TestConnects.withDetailedConnectResult(Sockets.ConnectParameter.of(endpoint,timeout),executor)
                    .thenApply(result->result!=null && result.success()?port:null);
            case BIND ->
                ServerSockets.TestBinds.withDetailedBindResult(ServerSockets.BindParameter.of(endpoint,null),executor)
                    .thenApply(result->result!=null && result.failure()?port:null);
        };
    }

    @Getter
    @ToString
    @AllArgsConstructor(staticName="of")
    @lombok.Builder(builderClassName="Builder")
    public static class Result implements Printable {

        @lombok.Singular
        private final List<Scan> scans;

        @Getter
        @ToString
        @AllArgsConstructor(staticName="of")
        @lombok.Builder(builderClassName="Builder")
        public static class Scan {
            private final InetAddress address;

            @lombok.Singular
            private final List<PortStatus> portStatuses;

            @Getter
            @ToString
            @AllArgsConstructor(staticName="of")
            @lombok.Builder(builderClassName="Builder")
            public static class PortStatus {
                private final int port;
                private final boolean success;
            }
        }

        @Override
        public void print(PrintStream out) {
            int scanIndex=1;
            for (var scan: scans) {
                out.printf("%3d Address %s:%n",scanIndex,scan.getAddress());
                int portStatusIndex=1;
                for (var portStatus: scan.portStatuses) {
                    out.printf("    %3d     %s%n",portStatusIndex,portStatus.getPort());
                    portStatusIndex++;
                }
                scanIndex++;
            }
        }
    }

    @SuppressWarnings({"java:S2583","ConstantConditions","java:S1117"})
    public Result scan() {
        log.atDebug().setMessage("Start scan.").log();
        try {
            Result.Builder resultBuilder=Result.builder();

            try (ManagedExecutor executor=executorSupplier.get()) {
                Executor limitedExecutor=createLimitedExecutor(executor);
                for (var addressSupplier: addressSuppliers) {
                    InetAddress address=addressSupplier.get();
                    log.atDebug().setMessage("Start scan of address {} using probe {}.").addArgument(address).addArgument(probe).log();

                    Result.Scan.Builder scanBuilder=Result.Scan.builder();

                    scanBuilder.address(address);

                    List<CompletableFuture<Integer>> futures=
                        ports.get().mapToObj(port->probePort(address,port,limitedExecutor)).toList();

                    CompletableFuture<List<Integer>> allFutures=CompletableFutures.allOf(futures);
                    List<Integer> occupiedPorts=
                        allFutures.thenApply(v->v.stream().filter(Objects::nonNull).toList()).join();

                    List<Result.Scan.PortStatus> portStatuses=
                        occupiedPorts.stream().map(port->Result.Scan.PortStatus.of(port,true)).toList();
                    scanBuilder.portStatuses(portStatuses);

                    Result.Scan scan=scanBuilder.build();
                    resultBuilder.scan(scan);
                    log.atDebug().setMessage("Stop scan of address {}.").addArgument(address).log();
                }
            } catch (IOException ex) {
                throw new IllegalStateException("Failure to scan!", ex);
            }
            return resultBuilder.build();
        } finally {
            log.atDebug().setMessage("Stop scan.").log();
        }
    }
}
