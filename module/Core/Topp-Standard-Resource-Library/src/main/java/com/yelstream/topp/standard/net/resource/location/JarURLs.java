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

package com.yelstream.topp.standard.net.resource.location;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-29
 */
@UtilityClass
public class JarURLs {
    private static URI removeInternalPath(URI uri) {
        String schemeSpecificPart=uri.getSchemeSpecificPart();
        return URI.create(schemeSpecificPart.split("!/")[0]);
    }

    public static URL removeInternalPath(URL url) {
        StandardProtocol.JAR.getProtocol().requireMatch(url);
        try {
            return removeInternalPath(url.toURI()).toURL();
        } catch (URISyntaxException | MalformedURLException ex) {
            throw new IllegalStateException("Failure to remove internal path; actual URL is '%s'!".formatted(url));
        }
    }

    private static URI extractFileURI(URI uri) {
        String schemeSpecificPart = uri.getSchemeSpecificPart();
        String uriText=schemeSpecificPart.contains("!/")?schemeSpecificPart.split("!/")[0]:schemeSpecificPart;
        return URI.create(uriText);
    }

    public static URL extractFileURL(URL url) {
        StandardProtocol.JAR.getProtocol().requireMatch(url);
        try {
            return extractFileURI(url.toURI()).toURL();
        } catch (URISyntaxException | MalformedURLException ex) {
            throw new IllegalStateException("Failure to extract file URL; actual URL is '%s'!".formatted(url));
        }
    }

    public static Path toFileSystemPath(URL url) {
        StandardProtocol.JAR.getProtocol().requireMatch(url);
        URL fileURL=extractFileURL(url);
        //FileURLs.requireMatch(fileURL);  //TO-DO: Fix!
        try {
            return Path.of(fileURL.toURI());
        } catch (URISyntaxException ex) {
            throw new IllegalStateException("Failure to obtain file system path; actual URL is '%s'!".formatted(url));
        }
    }

    public static String normalizePath(String path) {
        return (path.replaceFirst("/$","")+"/").replaceFirst("^/","");
    }

    public static List<String> findResources(ClassLoader classLoader,
                                             String path) throws IOException {
        return findResources(classLoader,path,null);
    }

    public static List<String> findResources(ClassLoader classLoader,
                                             String path,
                                             UnaryOperator<Stream<JarEntry>> streamOperator) throws IOException {
        String normalizedPath=normalizePath(path);
        URL resource=classLoader.getResource(normalizedPath);
        if (streamOperator==null) {
            streamOperator=UnaryOperator.identity();
        }
        return findResources(normalizedPath,resource,streamOperator);
    }

    private static List<String> findResources(String normalizedPath,
                                              URL resource,
                                              UnaryOperator<Stream<JarEntry>> streamOperator) throws IOException {
        StandardProtocol.JAR.getProtocol().requireMatch(resource);
        Path fileSystemPath=toFileSystemPath(resource);
        try (JarFile file=new JarFile(fileSystemPath.toFile())) {
            return streamOperator.apply(file.stream())
                                 .map(JarEntry::getName)
                                 .filter(name -> name.startsWith(normalizedPath))  //Note: This includes the root itself, 'normalizedPath'!
                                 .toList();
        }
    }


    public static List<String> findResources(String path,
                                             URL resource) throws IOException {
        String normalizedPath=normalizePath(path);
        return findResources(normalizedPath,resource,UnaryOperator.identity());
    }

}
