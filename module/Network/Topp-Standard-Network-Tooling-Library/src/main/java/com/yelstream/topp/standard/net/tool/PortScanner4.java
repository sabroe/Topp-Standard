package com.yelstream.topp.standard.net.tool;

import com.yelstream.topp.standard.net.Sockets;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PortScanner4 {

    private static final int TIMEOUT_MS = 2000; // Timeout for connection attempt in milliseconds

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Using virtual threads executor (adjust as needed)
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();  //TODO: Autoclosable!

        List<CompletableFuture<Sockets.SocketConnectivity2>> futures = new ArrayList<>();

        System.out.println("Phase #1!");

        for (int port = 0; port < 65536; port++) {
//        for (int port = 3000; port < 3000+1; port++) {
            int finalPort = port;

            CompletableFuture<Sockets.SocketConnectivity2> future=
                Sockets.TestConnects.withSocketConnectivity2(Sockets.ConnectParameter.of(new InetSocketAddress("localhost", finalPort), Duration.ofMillis(TIMEOUT_MS)),executor);

            futures.add(future);
        }

        System.out.println("Phase #2!");

        List<Sockets.SocketConnectivity2> results = new ArrayList<>();

        @SuppressWarnings({"unchecked","rawtypes"})
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        allFutures.thenRun(() -> {
            for (CompletableFuture<Sockets.SocketConnectivity2> future : futures) {
                try {
                    Sockets.SocketConnectivity2 result = future.get();
                    if (result != null) {
                        if (result.success()) {
                            results.add(result);
                        }
//                        System.out.println("Received data: " + result); // Log when data is received
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).join();

        executor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("Port scanning completed in " + (endTime - startTime) + " milliseconds");

        System.out.println("Scan Results:");
        AtomicInteger index=new AtomicInteger();
        results.forEach(x->{
            InetSocketAddress address=(InetSocketAddress)x.getConnectParameter().getEndpoint();
            System.out.println(index.getAndIncrement()+": "+address.getPort()+" "+x.getConnectParameter());
            ;
        });
    }
}
