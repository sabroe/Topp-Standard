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

package com.yelstream.topp.standard.resource.net.name.memory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;

public class MemoryURLStreamHandler extends URLStreamHandler {

    /*
     * WIP! Compile-time check only.
     */

    // In-memory storage (for demonstration; could be replaced with a more robust store)
    private static final Map<String, byte[]> memoryStore = new HashMap<>();

    // Add content to the in-memory store (for testing or initialization)
    public static void put(String path, byte[] data) {
        memoryStore.put(path, data);
    }

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return new MemURLConnection(url);
    }

    private static class MemURLConnection extends URLConnection {
        private final byte[] data;

        protected MemURLConnection(URL url) throws IOException {
            super(url);
            String path = url.getPath();
            if (path.startsWith("/")) {
                path = path.substring(1); // Remove leading slash
            }
            data = memoryStore.get(path);
            if (data == null) {
                throw new IOException("No data found for mem:" + path);
            }
        }

        @Override
        public void connect() throws IOException {
            // No-op: data is already in memory
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }
    }
}
