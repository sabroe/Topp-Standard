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

import com.yelstream.topp.standard.net.resource.identification.FileURIs;
import com.yelstream.topp.standard.net.resource.location.protocol.StandardProtocol;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
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
     * Gets the file path referred by a URL.
     * @param url URL.
     * @return File path.
     */
    public static Path toPath(URL url) {
        URI uri= StandardProtocol.File.getProtocol().toURI(url);
        return FileURIs.toPath(uri);
    }

    /**
     * Gets the file-channel for the file path referred by a URL.
     * @param url URL.
     * @param options Options for hos to open the file.
     * @return File-channel.
     */
    public static FileChannel openFileChannel(URL url,
                                              OpenOption... options) throws IOException {
        URI uri=StandardProtocol.File.getProtocol().toURI(url);
        return FileURIs.openFileChannel(uri,options);
    }

    /**
     * Gets the file-channel for the file path referred by a URL.
     * @param url URL.
     * @return File-channel.
     */
    public static FileChannel openFileChannel(URL url) throws IOException {
        URI uri=StandardProtocol.File.getProtocol().toURI(url);
        return FileURIs.openFileChannel(uri);
    }



    public static String normalizePath(String path) {
        return (path.replaceFirst("/$", "") + "/").replaceFirst("^/", "");
    }

    public static List<String> findResources(ClassLoader classLoader,
                                             String path) throws IOException {
        return findResources(classLoader,path,null);
    }

    public static List<String> findResources(ClassLoader classLoader,
                                             String path,
                                             UnaryOperator<Stream<Path>> streamOperator) throws IOException {
        String normalizedPath=normalizePath(path);
        URL resource=classLoader.getResource(normalizedPath);
        if (streamOperator==null) {
            streamOperator=UnaryOperator.identity();
        }
        return findResources(normalizedPath,resource,streamOperator);
    }

    private static List<String> findResources(String normalizedPath,
                                              URL resource,
                                              UnaryOperator<Stream<Path>> streamOperator) throws IOException {
        StandardProtocol.File.getProtocol().requireMatch(resource);
        Path fileSystemPath=toPath(resource);
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
        String normalizedPath=normalizePath(path);
        return findResources(normalizedPath,resource,UnaryOperator.identity());
    }
}
