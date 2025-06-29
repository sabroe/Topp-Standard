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

package com.yelstream.topp.standard.load.clazz.util.file;

import com.yelstream.topp.standard.load.clazz.ClassLoaders;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-29
 */
@UtilityClass
public class FileURLs {
    /**
     * URL protocol.
     */
    public static final String PROTOCOL="file";

    public static boolean matches(URL url) {
        return url.getProtocol().equals(PROTOCOL);
    }

    public static void requireMatch(URL url) {
        if (!matches(url)) {
            throw new IllegalArgumentException("Failure to match URL; URL protocol does not indicate a file URL, actual URL is '%s'!".formatted(url));
        }
    }

    public static Path toFileSystemPath(URL url) {
        requireMatch(url);
        try {
            return Path.of(url.toURI());
        } catch (URISyntaxException ex) {
            throw new IllegalStateException("Failure to obtain file system path; actual URL is '%s'!".formatted(url));
        }
    }

    public static List<String> findResources(ClassLoader classLoader,
                                             String path) throws IOException {
        return findResources(classLoader,path,null);
    }

    public static List<String> findResources(ClassLoader classLoader,
                                             String path,
                                             UnaryOperator<Stream<Path>> streamOperator) throws IOException {
        String normalizedPath=ClassLoaders.normalizePath(path);
        URL resource=classLoader.getResource(normalizedPath);
        if (streamOperator==null) {
            streamOperator=UnaryOperator.identity();
        }
        return findResources(normalizedPath,resource,streamOperator);
    }

    private static List<String> findResources(String normalizedPath,
                                              URL resource,
                                              UnaryOperator<Stream<Path>> streamOperator) throws IOException {
        requireMatch(resource);
        Path fileSystemPath=toFileSystemPath(resource);
        String fileSeparator=fileSystemPath.getFileSystem().getSeparator();
        return streamOperator.apply(Files.walk(fileSystemPath))
                .filter(path -> Files.isRegularFile(path) || Files.isDirectory(path))
                .map(path -> {
                    String relativePath = fileSystemPath.relativize(path).toString().replace(fileSeparator, "/");
                    if (Files.isDirectory(path)) {
                        if (relativePath.isEmpty()) {
                            return normalizedPath;  //Note: This is the root ""!
                        } else {
                            return normalizedPath + relativePath + "/";  //Directory name, got a trailing "/"!
                        }
                    }
                    return normalizedPath + relativePath;  //File name, got no trailing "/"!
                })
                .toList();
    }


    public static List<String> findResources(String path,
                                             URL resource) throws IOException {
        String normalizedPath=ClassLoaders.normalizePath(path);
        return findResources(normalizedPath,resource,UnaryOperator.identity());
    }

}
