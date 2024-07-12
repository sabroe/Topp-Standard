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

import com.yelstream.topp.standard.util.concurrent.CompletableFutures;
import com.yelstream.topp.standard.util.concurrent.ManagedExecutor;
import com.yelstream.topp.standard.util.function.SupplierWithException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
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
//    private final List<NetworkInterface> networkInterfaces;

//    @lombok.Singular
    @lombok.Builder.Default
    private final List<SupplierWithException<InetAddress,IOException>> addressSuppliers=List.of(()->InetAddress.getByName("localhost"));

    @lombok.Builder.Default
    private final Supplier<IntStream> ports=()->IntStream.range(0,65536);

    @lombok.Builder.Default
    private final Duration timeout=Duration.ofMillis(2000);

    @lombok.Builder.Default
    private final Supplier<ManagedExecutor> executorSupplier=()-> ManagedExecutor.of(Executors.newVirtualThreadPerTaskExecutor());

    @ToString
    @Getter
    @AllArgsConstructor
    public static class Result {
        private final List<Sockets.DetailedConnectResult> results;

    }

    @SuppressWarnings({"java:S2583","ConstantConditions","java:S1117"})
    public Result scan() {
        Result result=null;

        try (ManagedExecutor executor=executorSupplier.get()) {

//            SupplierWithException<InetAddress,IOException> addressSupplier=Inet4Addresses.StandardAddress.LoopbackAddress.getAddressSupplier();
//            InetAddress address=addressSupplier.get();

            for (var addressSupplier: addressSuppliers) {
                InetAddress address=addressSupplier.get();
System.out.println("Address: "+address);

                List<CompletableFuture<Sockets.DetailedConnectResult>> futures=
                    ports.get().mapToObj(port -> {
                        return Sockets.TestConnects.withDetailedConnectResult(Sockets.ConnectParameter.of(new InetSocketAddress(address, port), timeout), executor);
                    }
                    ).toList();

                CompletableFuture<List<Sockets.DetailedConnectResult>> allFutures=CompletableFutures.allOf(futures);

                List<Sockets.DetailedConnectResult> results=
                    allFutures.thenApply(v -> v.stream().filter(r -> r!=null && r.success()).toList()).join();

                result=new Result(results);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @SuppressWarnings({"java:S1068","java:S1450","FieldCanBeLocal"})
    public static class Builder {
        private Supplier<IntStream> ports;

//        private List<SupplierWithException<InetAddress,IOException>> addresses=List.of(()->InetAddress.getByName("localhost"));

    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
//        DurationWatches.

//        System.setProperty("java.net.preferIPv6Addresses", "true");

        System.out.println("IP #1: "+InetAddresses.resolve(InetAddresses.StandardAddress.getAllSuppliers()));
        System.out.println("IP #2: "+InetAddresses.resolveDistinct(InetAddresses.StandardAddress.getAllSuppliers()));
        System.out.println();

        System.out.println("IPv4 #1: "+InetAddresses.resolve(Inet4Addresses.StandardAddress.getAllSuppliers()));
        System.out.println("IPv4 #2: "+InetAddresses.resolveDistinct(Inet4Addresses.StandardAddress.getAllSuppliers()));
        System.out.println();

        System.out.println("IPv6 #1: "+InetAddresses.resolve(Inet6Addresses.StandardAddress.getAllSuppliers()));
        System.out.println("IPv6 #2: "+InetAddresses.resolveDistinct(Inet6Addresses.StandardAddress.getAllSuppliers()));
        System.out.println();

        SocketScanner.Builder builder=SocketScanner.builder().ports(()->IntStream.range(0,65535)).timeout(Duration.ofSeconds(1));
        builder.addressSuppliers(InetAddresses.distinct(Inet4Addresses.StandardAddress.getAllSuppliers()));
        SocketScanner scanner=builder.build();

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


@Getter
@ToString
class Result {
    private final Map<String, List<TestResult>> results = new HashMap<>();

    public void addResult(String testType, NetworkInterface networkInterface, TestResult result) {
        String key = testType + "@" + networkInterface.getName();
        results.computeIfAbsent(key, k -> new ArrayList<>()).add(result);
    }

    public List<TestResult> getResults(String testType, NetworkInterface networkInterface) {
        String key = testType + "@" + networkInterface.getName();
        return results.getOrDefault(key, Collections.emptyList());
    }

    public Map<String, List<TestResult>> getResults() {
        return Collections.unmodifiableMap(results);
    }
}

interface TestResult {
    boolean isSuccess();
    String getDescription();
}

class TcpTestResult implements TestResult {
    private final InetSocketAddress address;
    private final boolean success;

    public TcpTestResult(InetSocketAddress address, boolean success) {
        this.address = address;
        this.success = success;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getDescription() {
        return "TCP test result for " + address.toString();
    }

    public InetSocketAddress getAddress() {
        return address;
    }
}