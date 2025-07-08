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

package com.yelstream.topp.standard.load.clazz;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * Utility addressing {@link ClassLoader} instances.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@UtilityClass
public class ClassLoaders {
    public static ClassLoader getPlatformClassLoader() {
        return ClassLoader.getPlatformClassLoader();
    }

    public static ClassLoader getSystemClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }

    public static ClassLoader getModuleClassLoader(Module module) {
        return module.getClassLoader();
    }

    public static ClassLoader getModuleClassLoader(Class<?> clazz) {
        return getModuleClassLoader(clazz.getModule());
    }

    /**
     * Gets the context classloader for a specific thread.
     * @param thread Thread.
     * @return Classloader.
     */
    public static ClassLoader getContextClassLoader(Thread thread) {
        return thread.getContextClassLoader();
    }

    /**
     * Gets the context classloader for the current thread.
     * @return Classloader.
     */
    public static ClassLoader getContextClassLoader() {
        return getContextClassLoader(Thread.currentThread());
    }

    public static ClassLoader getClassLoader(Class<?> clazz) {
        return clazz.getClassLoader();
    }

    /**
     * Normalizes resource paths/resource names.
     * <p>
     *    Removes leading/trailing slashes, ensures trailing slash.
     * </p>
     * <p>
     *     Note that the empty path will be normalized to "/".
     * </p>
     * <p>
     *     Normalized paths are valid for resource-lookup using 
     *     {@link ClassLoader#getResource(String)} and {@link ClassLoader#getResourceAsStream(String)}.
     * </p>
     * <p>
     *     Example indicating the mappings:
     * </p>
     * <ul>
     *     <li>"path/to/dir/" maps to "path/to/dir/"</li>
     *     <li>"/path/to/dir" maps to "path/to/dir/"</li>
     *     <li>"" maps to ""</li>
     *     <li>"/" maps to ""</li>
     * </ul>
     * <p>
     *     Note that for addressing the classloader,
     *     e.g. through {@link ClassLoader#getResource(String)},
     *     the root is the empty string "",
     *     while paths/directories end in "/".
     * </p>
     * @param path Resource path/resource name.
     * @return Normalized resource path/resource name.
     */
    public static String normalizePath(String path) {
        return (path.replaceFirst("/$","")+"/").replaceFirst("^/", "");
        /*
         * Note:
         *   Classloader:
         *       return (path.replaceFirst("/$","")+"/").replaceFirst("^/", "");
         *       Root is "", directories end in "/" (name is a path/directory if it is "" or ends in "/").
         *   UNIX:
         *       return path.replaceFirst("^/", "").replaceFirst("/$","")+"/";
         *       Root is "/", directories end in "/" (name is a path/directory if it ends in "/").
         */
        //TO-DO: Consider creating a 'NameScheme' object for path handling different styles { "Classloader", "UNIX", <neutral no-slash-added> }.
    }

    public static boolean existsResource(ClassLoader classLoader,
                                         String name) {
        URL resourceURL=classLoader.getResource(name);
        return resourceURL!=null;
    }

    public static void requireResource(ClassLoader classLoader,
                                       String name) {
        if (!existsResource(classLoader,name)) {
            throw new IllegalArgumentException("Failure to verify the presence of named resource; name is '%s'!".formatted(name));
        }
    }






    public static List<String> findResources(Class<?> clazz,
                                             String path) throws IOException {
        String normalizedPath=normalizePath(path);

        List<String> result=null;

        ClassLoader classLoader=clazz.getClassLoader();
        URL resource=classLoader.getResource(normalizedPath);
        if (resource!=null) {
            result=findResources(classLoader,path);
        }

        if (result==null) {
            URL resource2=clazz.getProtectionDomain().getCodeSource().getLocation();
System.out.println("Code-source location: "+resource2);
            if (resource2!=null) {
                result=findResources(classLoader,path,resource2);
            }
        }

        if (result==null) {
            result=Collections.emptyList();
        }

        return result;
    }

    public static List<String> findResources(ClassLoader classLoader,
                                             String path) throws IOException {
        String normalizedPath=normalizePath(path);
        URL resource=classLoader.getResource(normalizedPath);
        if (resource==null) {
            return null;
        } else {
            return findResources(classLoader,normalizedPath,resource);
        }
    }


    public static List<String> findResources(ClassLoader classLoader,
                                             String normalizedPath,
                                             URL resource) throws IOException {
System.out.println("RESOURCE URL #1: "+resource);

try {
    if (resource.getProtocol().equals("file") && !resource.getPath().endsWith(".jar")) {  //TO-DO: If file-URL is a directory in the file-system!
        Path p1 = Path.of(resource.toURI());
        System.out.println("p1 exists: " + Files.exists(p1));
        resource = URI.create(resource.toString().replace("classes/java/main", "resources/main")).toURL();
        Path p2 = Path.of(resource.toURI());
        System.out.println("p2 exists: " + Files.exists(p2));
    }
} catch (URISyntaxException ex) {
    throw new IllegalStateException(ex);
}

        if (resource.getProtocol().equals("file") && resource.getPath().endsWith(".jar")) {
            resource=URI.create("jar:"+resource+"!/").toURL();
        }

System.out.println("RESOURCE URL #2: "+resource);

return null;
/*
        return
            switch (resource.getProtocol()) {  //TO-DO: Use URL-scanners, please!
                case "jar" -> JARURLs.findResources(normalizedPath,resource);
                case "file" -> FileURLs.findResources(normalizedPath,resource);
                default -> null;
            };
*/
    }
}
