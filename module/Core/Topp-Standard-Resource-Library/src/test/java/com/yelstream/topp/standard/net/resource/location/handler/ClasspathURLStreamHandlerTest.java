package com.yelstream.topp.standard.net.resource.location.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Test of {@link ClasspathURLStreamHandler}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-13
 */
class ClasspathURLStreamHandlerTest {

    private static String getContent(URL url) throws IOException {
        String content;
        try (InputStream in=url.openStream()) {
            content=new String(in.readAllBytes(),StandardCharsets.UTF_8);
        }
        return content;
    }

    @Test
    void basicAccess() throws IOException {
        URI uri=URI.create("classpath:///example/resource.txt");
        URL url=URL.of(uri,ClasspathURLStreamHandler.of());
        String content=getContent(url);
        Assertions.assertEquals("Krampus!",content);
    }

    @Test
    void accessUsingSpecificClassLoader() throws IOException {
        URI uri=URI.create("classpath://context/example/resource.txt");
        URL url=URL.of(uri,ClasspathURLStreamHandler.of());
        String content=getContent(url);
        Assertions.assertEquals("Krampus!",content);
    }

/*
    @Test
    void xxxaccessUsingSpecificClassLoader() throws IOException {
        URI uri=URI.create("classpath://xxx/example/resource.txt");
        URL url=URL.of(uri,ClasspathURLStreamHandler.of());
        String content=getContent(url);
        Assertions.assertEquals("Krampus!",content);
    }
*/
}
