package com.yelstream.topp.standard.net.tool;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortOpener {

    private static final int TIMEOUT_MS = 1000; // Timeout for binding attempt in milliseconds

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Using virtual threads executor (adjust as needed)
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        List<CompletableFuture<Integer>> futures = new ArrayList<>();

        // Scan all ports from 1 to 65535
        for (int port = 1; port <= 65535; port++) {
            int finalPort = port;
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                try {
                    ServerSocket serverSocket = new ServerSocket(finalPort);
                    serverSocket.close();
/*
                    ServerSocket serverSocket = new ServerSocket(finalPort);
                    serverSocket.setSoTimeout(TIMEOUT_MS); // Set timeout for accept
                    serverSocket.accept(); // Attempt to accept a connection (blocks for TIMEOUT_MS)
                    serverSocket.close();
*/

                    return finalPort;
                } catch (Exception e) {
                    // Port is not available for listening
                    return null;
                }
            }, executor);

            futures.add(future);
        }

        List<Integer> openPorts = new ArrayList<>();

        @SuppressWarnings({"unchecked","rawtypes"})
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        allFutures.thenRun(() -> {
            for (CompletableFuture<Integer> future : futures) {
                try {
                    Integer port = future.get(); // Get the result of each future
                    if (port != null) {
                        openPorts.add(port);
                        System.out.println("Port " + port + " is open for listening");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).join();

        executor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("Port scanning completed in " + (endTime - startTime) + " milliseconds");

        // Print all open ports after scanning is completed
        System.out.println("Open Ports:");
        openPorts.forEach(System.out::println);
    }
}
