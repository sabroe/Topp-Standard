/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Scans and tests for occupied and available sockets within a range of ports.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-05
 */
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor
public class SocketScanner {
    private final List<NetworkInterface> networkInterfaces;

    @lombok.Builder.Default
    private final List<InetAddress> addresses=null;//=List.of(InetAddress.getByName("localhost"));

    @lombok.Builder.Default
    private final Supplier<IntStream> ports=()->IntStream.range(0,65536);

    @lombok.Builder.Default
    private final Duration timeout=Duration.ofMillis(2000);

    @lombok.Builder.Default
    private final Executor executor=null;

    @lombok.Builder.Default
    private final Supplier<ExecutorService> executorSupplier=Executors::newVirtualThreadPerTaskExecutor;

    @ToString
    @Getter
    @AllArgsConstructor
    public static class Result {
        private final List<Sockets.DetailedConnectResult> results;

    }

    @SuppressWarnings({"java:S2583","ConstantConditions","java:S1117"})
    public Result scan() {

        List<Sockets.DetailedConnectResult> results = new ArrayList<>();

        try (ExecutorService executorService=(this.executor!=null?null:executorSupplier.get())) {
            Executor executor=this.executor!=null?this.executor:executorService;

            List<CompletableFuture<Sockets.DetailedConnectResult>> futures = new ArrayList<>();

            ports.get().forEach(port-> {
                CompletableFuture<Sockets.DetailedConnectResult> future =
                        Sockets.TestConnects.withDetailedConnectResult(Sockets.ConnectParameter.of(new InetSocketAddress("localhost", port), timeout), executor);

                futures.add(future);
            });

            @SuppressWarnings({"unchecked", "rawtypes"})
            CompletableFuture<Void> allFutures=CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

            allFutures.thenRun(() -> {
                for (CompletableFuture<Sockets.DetailedConnectResult> future : futures) {
                    try {
                        Sockets.DetailedConnectResult result = future.get();
                        if (result != null) {
                            if (result.success()) {
                                results.add(result);
                            }
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }).join();
        }

        return new Result(results);
    }

    @SuppressWarnings({"java:S1068","java:S1450","FieldCanBeLocal"})
    public static class Builder {
        private Supplier<IntStream> ports;

        public Builder ports(Supplier<IntStream> portRangeSupplier) {
            this.portRangeSupplier=portRangeSupplier;
            return this;
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        SocketScanner scanner=SocketScanner.builder().portSupplier(()->IntStream.range(0,65535)).timeout(Duration.ofSeconds(1)).build();
        SocketScanner.Result result=scanner.scan();

        long endTime = System.currentTimeMillis();
        System.out.println("Port scanning completed in " + (endTime - startTime) + " milliseconds");

        System.out.println("Scan Results:");
        AtomicInteger index=new AtomicInteger();
        result.getResults().forEach(x->{
            InetSocketAddress address=(InetSocketAddress)x.getConnectParameter().getEndpoint();
            System.out.println(index.getAndIncrement()+": "+address.getPort()+" "+x.getConnectParameter());
        });

    }
}
