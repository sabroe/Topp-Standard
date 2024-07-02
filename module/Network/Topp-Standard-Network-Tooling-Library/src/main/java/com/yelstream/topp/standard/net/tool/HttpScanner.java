package com.yelstream.topp.standard.net.tool;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class HttpScanner {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final List<String> PATHS =
        List.of(
            "/q/arc",
            "/q/arc/beans",
            "/q/arc/observers",
            "/q/arc/removed-beans",
            "/q/dev-ui",
            "/q/health",
            "/q/health-ui",
            "/q/health/group",
            "/q/health/group/*",
            "/q/health/live",
            "/q/health/ready",
            "/q/health/started",
            "/q/health/well",
            "/q/metrics",
            "/q/openapi",
            "/q/swagger-ui"
        );

    private static final int SIMULTANEOUS_REQUESTS = 5;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(SIMULTANEOUS_REQUESTS);
        HttpClient client = HttpClient.newBuilder()
                .executor(executorService)
                .build();  //TO-DO: HttpClient auto-closable!

        List<CompletableFuture<HttpResponse<String>>> futures = PATHS.stream()
                .map(path -> {
                    URI uri = URI.create("http://" + HOST + ":" + PORT + path);
                    HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
                    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                })
                .toList();

        @SuppressWarnings({"unchecked","rawtypes"})
        CompletableFuture<Void> allFutures = CompletableFuture.allOf((CompletableFuture<Void>[])futures.toArray(new CompletableFuture[0]));

        allFutures.thenRun(() -> {
            System.out.println("HTTP Scan Report:");
            futures.forEach(future -> {
                future.thenAccept(response -> {
                    System.out.println("Path: " + response.uri().getPath());
                    System.out.println("Status Code: " + response.statusCode());
                    System.out.println("Body: " + response.body().substring(0, Math.min(response.body().length(), 100)));
                }).exceptionally(e -> {
                    System.out.println("Request failed: " + e.getMessage());
                    return null;
                });
            });
        }).join();

        executorService.shutdown();
    }
}
