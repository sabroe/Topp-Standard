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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-22
 */
@UtilityClass
public class Resources {

/*
    private static String normalizePath(String path) {
        String normalized=path.startsWith("/")?path:"/"+path;
        return normalized.endsWith("/")?normalized.substring(0,normalized.length()-1):normalized;
    }
*/

    /**
     * Normalizes a path by removing leading and trailing slashes.
     * @param path Path to normalize.
     * @return Normalized path.
     */
    public static String normalizePath(String path) {
        String normalized = path.replaceAll("^/+", "").replaceAll("/+$", "");
        return normalized.isEmpty() ? "" : normalized;
    }

    private static String normalizeNamespaceBaseUri(String uri) {
        return uri.endsWith("/")?uri:uri+"/";
    }

    public static URL getLocationOfClass(Class<?> clazz) throws IOException {
        CodeSource codeSource=clazz.getProtectionDomain().getCodeSource();
        if (codeSource==null || codeSource.getLocation()==null) {
            throw new IOException("Cannot determine JAR location for caller class: "+clazz.getName());
        }
        return codeSource.getLocation();

        /*
         * Possible forms of results:
         *  1) file:/C:/Project/Topp-Work/Topp-Standard/module/Data/Demo/Airport-LHR-HAL-CIM-Schema-Library/build/classes/java/main/
         *     If run in IDEA.
         *  2) file:/C:/Project/Topp-Work/Topp-Standard/module/Data/Demo/Airport-LHR-HAL-CIM-Schema-Library/build/libs/Airport-LHR-HAL-CIM-Schema-Library-0.3.2.jar
         *     If run from Gradle as a JAR.
         *  3) file:/C:/Project/Topp-Work/Topp-Standard/module/Data/Demo/Airport-LHR-HAL-CIM-Schema-Library/build/classes/java/main/
         *     If run from Gradle as a built class.
         */
    }


    /**
     * Gets the code base root URL for a class by subtracting the class path.
     * Examples:
     * - jar:file:/path/to/jar!/com/example/MyClass.class -> jar:file:/path/to/jar!/
     * - file:/path/to/classes/com/example/MyClass.class -> file:/path/to/classes/
     * - http://example.com/lib/com/example/MyClass.class -> http://example.com/lib/
     * @param clazz Class to locate.
     * @return Code base root URL.
     * @throws IOException If the location cannot be determined.
     */
    public static URL getCodeBaseOfClass(Class<?> clazz) throws IOException {
        // Get .class file URL via ClassLoader
        String classResource = clazz.getName().replace('.', '/') + ".class";
        URL classUrl = clazz.getClassLoader().getResource(classResource);
        if (classUrl != null) {
            String urlStr = classUrl.toString();
            // Handle nested JARs (e.g., jar:file:/outer.jar!/BOOT-INF/lib/inner.jar!/com/example/MyClass.class)
            int lastBangIndex = urlStr.lastIndexOf("!/");
            if (lastBangIndex >= 0 && urlStr.startsWith("jar:")) {
                String classPath = classResource.replace('/', '/');
                int classPathIndex = urlStr.lastIndexOf(classPath);
                if (classPathIndex >= lastBangIndex) {
                    String rootStr = urlStr.substring(0, classPathIndex);
                    try {
                        return URI.create(rootStr.endsWith("!/") ? rootStr : rootStr + "!/").toURL();
                    } catch (MalformedURLException e) {
                        throw new IOException("Invalid JAR root URL: " + rootStr, e);
                    }
                }
            }
            // Handle other protocols (file:, http:, vfs:)
            String classPath = classResource.replace('/', '/');
            int classPathIndex = urlStr.lastIndexOf(classPath);
            if (classPathIndex >= 0) {
                String rootStr = urlStr.substring(0, classPathIndex);
                try {
                    return URI.create(rootStr.endsWith("/") ? rootStr : rootStr + "/").toURL();
                } catch (MalformedURLException e) {
                    throw new IOException("Invalid root URL: " + rootStr, e);
                }
            }
            System.err.println("Warning: Cannot extract class path from URL: " + urlStr);
        }

        // Fallback to CodeSource
        CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
        if (codeSource != null && codeSource.getLocation() != null) {
            URL location = codeSource.getLocation();
            String urlStr = location.toString();
            if ("file".equals(location.getProtocol()) && urlStr.toLowerCase().endsWith(".jar")) {
                try {
                    return URI.create("jar:" + urlStr + "!/").toURL();
                } catch (MalformedURLException e) {
                    throw new IOException("Invalid JAR URL: jar:" + urlStr + "!/", e);
                }
            }
            try {
                return URI.create(urlStr.endsWith("/") ? urlStr : urlStr + "/").toURL();
            } catch (MalformedURLException e) {
                throw new IOException("Invalid CodeSource URL: " + urlStr, e);
            }
        }

        throw new IOException("Cannot determine location for class: " + clazz.getName());
    }

}
