package com.yelstream.topp.standard.net.tool;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortScanner3 {

    private static final int TIMEOUT_MS = 1000; // Timeout for connection attempt in milliseconds

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Using virtual threads executor (adjust as needed)
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();  //TODO: Autoclosable!

        List<CompletableFuture<String>> futures = new ArrayList<>();

        // Scan all ports from 1 to 65535
        for (int port = 1; port <= 65535; port++) {
            int finalPort = port;
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress("localhost", finalPort), TIMEOUT_MS);
                    String result = "Port " + finalPort + " is open";
                    socket.close();
                    return result;
                } catch (Exception e) {
                    // Port is closed or connection timed out
                    // Uncomment below to print closed ports as well
                    // System.out.println("Port " + port + " is closed");
                    return null;
                }
            }, executor);

            futures.add(future);
        }

        List<String> results = new ArrayList<>();

        @SuppressWarnings({"unchecked","rawtypes"})
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        allFutures.thenRun(() -> {
            for (CompletableFuture<String> future : futures) {
                try {
                    String result = future.get(); // Get the result of each future
                    if (result != null) {
                        results.add(result);
                        System.out.println("Received data: " + result); // Log when data is received
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).join();

        executor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("Port scanning completed in " + (endTime - startTime) + " milliseconds");

        // Print all results after scanning is completed
        System.out.println("Scan Results:");
        results.forEach(System.out::println);
    }
}
