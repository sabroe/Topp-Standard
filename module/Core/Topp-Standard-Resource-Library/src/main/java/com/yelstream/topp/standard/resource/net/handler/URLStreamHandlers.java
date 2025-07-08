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

package com.yelstream.topp.standard.resource.net.handler;

import lombok.experimental.UtilityClass;
import java.net.URLStreamHandler;

/**
 * Utilities addressing instances of {@link URLStreamHandler}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
@UtilityClass
public class URLStreamHandlers {


    /*
     * System.setProperty("java.protocol.handler.pkgs", "your.package.name");  //Semantics?
     *
     * Place the MemURLStreamHandler in a package like your.package.name.protocols.mem,
     * and the JVM will automatically discover it for URLs starting with "mem:".   <- Garbage?
     */

/*
    public static void main(String[] args) throws Exception {
        // Register the handler
        URL.setURLStreamHandlerFactory(protocol ->
                "mem".equalsIgnoreCase(protocol) ? new MemURLStreamHandler() : null);

        // Add some test data
        MemURLStreamHandler.put("test.txt", "Hello, in-memory world!".getBytes());

        // Test the URL
        URL url = new URL("mem:/test.txt");
        try (InputStream is = url.openStream()) {
            System.out.println(new String(is.readAllBytes()));
        }
    }
*/

}
