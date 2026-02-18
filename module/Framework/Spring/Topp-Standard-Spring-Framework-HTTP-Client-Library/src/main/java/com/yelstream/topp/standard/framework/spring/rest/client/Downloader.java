package com.yelstream.topp.standard.framework.spring.rest.client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
@Service
class Downloader {

    private final RestClient restClient;

    // constructor injection

    public InputStream download(String url) throws IOException {
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(InputStream.class);
        } catch (RestClientException e) {
            throw new IOException("Download failed", e);
        }
    }
}
