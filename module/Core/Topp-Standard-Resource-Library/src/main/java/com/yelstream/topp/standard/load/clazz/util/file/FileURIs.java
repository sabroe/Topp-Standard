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

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-29
 */
@UtilityClass
public class FileURIs {
    /**
     * URI scheme.
     */
    public static final String SCHEME="file";

    public static boolean isSchemeFile(URI uri) {
        return SCHEME.equalsIgnoreCase(uri.getScheme());
    }

    public static void requireSchemeFile(URI uri) {
        if (isSchemeFile(uri)) {
            throw new IllegalArgumentException("Failure to verify URI scheme; URI is '%s'!".formatted(uri));
        }
    }

    public static URL toURL(URI uri) {  //TO-DO: Move to more generic utility!
        try {
            return uri.toURL();
        } catch (MalformedURLException ex) {
            throw new IllegalStateException("Failure to convert URI to URL; actual URI is '%s'!".formatted(uri));
        }
    }

    public static URL toFileURL(URI uri) {
        requireSchemeFile(uri);
        return toURL(uri);
    }

    /**
     * Gets the file path referred by a URI.
     * @param uri URI.
     * @return File path.
     */
    public static Path toPath(URI uri) {
        requireSchemeFile(uri);
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
