/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.net.resource.identification.type;

import com.yelstream.topp.standard.net.resource.identification.scheme.StandardScheme;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Utilities for file-specific URIs.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-29
 */
@UtilityClass
public class FileURIs {
    /**
     * Gets the file path referred by a URI.
     * @param uri URI.
     * @return File path.
     */
    public static Path toPath(URI uri) {
        StandardScheme.File.getScheme().requireMatch(uri);
        return Paths.get(uri);
    }

    /**
     * Gets the file-channel for the file path referred by a URI.
     * @param uri URI.
     * @param options Options for hos to open the file.
     * @return File-channel.
     */
    public static FileChannel openFileChannel(URI uri,
                                              OpenOption... options) throws IOException {
        Path path=toPath(uri);
        return FileChannel.open(path,options);
    }

    /**
     * Gets the file-channel for the file path referred by a URI.
     * @param uri URI.
     * @return File-channel.
     */
    public static FileChannel openFileChannel(URI uri) throws IOException {
        return openFileChannel(uri,StandardOpenOption.READ);
    }
}
