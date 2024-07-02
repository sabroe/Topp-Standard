package com.yelstream.topp.standard.net.tool;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PortScanner {

    private static final int TIMEOUT_MS = 1000; // Timeout for connection attempt in milliseconds

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Using virtual threads executor (adjust as needed)
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // Scan all ports from 1 to 65535
        for (int port = 1; port <= 65535; port++) {
            int finalPort = port;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress("localhost", finalPort), TIMEOUT_MS);
                    System.out.println("Port " + finalPort + " is open");
                    socket.close();
                } catch (Exception e) {
                    // Port is closed or connection timed out
                    // Uncomment below to print closed ports as well
                    // System.out.println("Port " + port + " is closed");
                }
            }, executor);

            futures.add(future);
        }

        try {
            @SuppressWarnings({"unchecked","rawtypes"})
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allFutures.get(10, TimeUnit.SECONDS); // Wait up to 10 seconds
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("Port scanning completed in " + (endTime - startTime) + " milliseconds");
    }
}
