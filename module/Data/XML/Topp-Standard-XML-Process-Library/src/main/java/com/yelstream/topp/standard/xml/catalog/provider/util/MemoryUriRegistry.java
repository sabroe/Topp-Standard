/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.xml.catalog.provider.util;

import lombok.experimental.UtilityClass;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.net.URI;

/**
 * Registry for memory: URIs with custom URLStreamHandler.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-22
 */
@UtilityClass
public final class MemoryUriRegistry {
    private static final Map<String, String> contentRegistry = new ConcurrentHashMap<>();

    public static void register(URI uri, String content) {
        if (!"memory".equals(uri.getScheme())) {
            throw new IllegalArgumentException("URI scheme must be 'memory': " + uri);
        }
        contentRegistry.put(uri.toString(), content);
    }

    public static String getContent(URI uri) {
        return contentRegistry.get(uri.toString());
    }

    public static URL createMemoryUrl(URI uri) throws IOException {
        if (!"memory".equals(uri.getScheme())) {
            throw new IllegalArgumentException("URI scheme must be 'memory': " + uri);
        }
        return URL.of(uri, new MemoryURLStreamHandler());
    }

    private static class MemoryURLStreamHandler extends URLStreamHandler {
        @Override
        protected URLConnection openConnection(URL url) throws IOException {
            return new MemoryURLConnection(url);
        }
    }

    private static class MemoryURLConnection extends URLConnection {
        protected MemoryURLConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
            //Empty!
        }

        @Override
        public java.io.InputStream getInputStream() throws IOException {
            String content = contentRegistry.get(url.toString());
            if (content == null) {
                throw new IOException("No content for memory URI: " + url);
            }
            return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        }
    }
}
