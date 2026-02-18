package com.yelstream.topp.standard.framework.spring.rest.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
class HttpConfig {

    @Bean
    RestClient restClient() {
        HttpClient nativeClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(nativeClient);
        factory.setReadTimeout(Duration.ofSeconds(120));

        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }
}
