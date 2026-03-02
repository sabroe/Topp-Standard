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

package com.yelstream.topp.standard.framework.spring.rest.client;

import lombok.experimental.UtilityClass;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;

@UtilityClass
public class RestClients {

    public static InputStream download0(RestClient restClient,
                                        String url) throws IOException {
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(InputStream.class);
        } catch (RestClientException e) {
            throw new IOException("Download failed", e);
        }
    }

    public static InputStream download(RestClient restClient,
                                       String url) throws IOException {
        try {
            return restClient.get()
                    .uri(URI.create(url))
                    .exchange((request, response) -> {
                        if (response.getStatusCode().isError()) {
                            // you can still return the body InputStream
                        }
                        return response.getBody();  // InputStream
                    });
        } catch (RestClientException e) {
            throw new IOException("Download failed", e);
        }
    }

    public static InputStream download2(RestClient restClient,
                                        String url) {
        return restClient.get()
            .uri(URI.create(url))
            .exchange((request, response) -> {
                if (response.getStatusCode().isError()) {
                    // you can still return the body InputStream
                }
                return response.getBody();
            });
    }

    /**
     * Downloads a resource as a live InputStream using Spring's RestClient
     * backed by java.net.http.HttpClient (JDK HttpClient).
     *
     * The underlying HTTP response is closed **only** when the returned InputStream is closed.
     *
     * @param url the target URL
     * @return live InputStream directly from the HTTP response body
     * @throws IOException on connection errors or non-success status
     */
    public static InputStream downloadAsStream(String url) throws IOException {
        // You can create & customize the underlying java.net.http.HttpClient
        HttpClient nativeClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        // Wrap it in Spring's JdkClientHttpRequestFactory (this is what makes RestClient use JDK HttpClient)
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(nativeClient);
        requestFactory.setReadTimeout(Duration.ofSeconds(60));

        // Build RestClient (can be a @Bean in real applications)
        RestClient restClient = RestClient.builder()
                .requestFactory(requestFactory)
                // Optional: .defaultHeader("Accept", "*/*")
                // Optional: .defaultUriVariables(...)
                .build();

        try {
            // retrieve() → throws on 4xx/5xx by default → we want InputStream even on error? Adjust if needed
            return restClient.get()
                    .uri(URI.create(url))
                    .retrieve()
                    .body(InputStream.class);   // ← This is the key: returns live streaming InputStream

        } catch (RestClientException e) {
            // RestClient wraps IO / Interrupted / etc → convert to IOException
            throw new IOException("Download failed: " + e.getMessage(), e);
        }
    }



}
