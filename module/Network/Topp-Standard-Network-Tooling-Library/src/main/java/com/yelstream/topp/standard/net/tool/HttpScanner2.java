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

package com.yelstream.topp.standard.net.tool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-02
 */
public class HttpScanner2 {

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

    static class HalLink {
        public String href;
        public HalLink(String href) { this.href = href; }
    }

    static class HalResource {
        @JsonProperty("_links")
        public Links links;
        public String path;
        public int statusCode;
        public String body;

        public HalResource(String path, int statusCode, String body) {
            this.links = new Links(path);
            this.path = path;
            this.statusCode = statusCode;
            this.body = body;
        }
    }

    static class Links {
        public HalLink self;

        public Links(String path) {
            this.self = new HalLink("http://" + HOST + ":" + PORT + path);
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(SIMULTANEOUS_REQUESTS);
        HttpClient client = HttpClient.newBuilder()
                .executor(executorService)
                .build();  //TO-DO: Auto-closable!

        List<CompletableFuture<HalResource>> futures = PATHS.stream()
                .map(path -> {
                    URI uri = URI.create("http://" + HOST + ":" + PORT + path);
                    HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
                    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                            .thenApply(response -> new HalResource(
                                    path,
                                    response.statusCode(),
                                    response.body().substring(0, Math.min(response.body().length(), 100))
                            )).exceptionally(e -> {
                                e.printStackTrace();
                                return new HalResource(path, 500, "Request failed: " + e.getMessage());
                            });
                })
                .toList();

        @SuppressWarnings({"unchecked","rawtypes"})
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        allFutures.thenRun(() -> {
            List<HalResource> results = futures.stream()
                    .map(CompletableFuture::join)
                    .toList();
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                String jsonOutput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results);
                System.out.println(jsonOutput);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).join();

        executorService.shutdown();
    }
}
