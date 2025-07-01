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

package com.yelstream.topp.standard.resource.io.source;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link InputSource}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@UtilityClass
public class InputSources {
    /**
     * Creates an input source.
     * @param streamSupplier Factory of input-streams.
     * @param channelSupplier Factory of readable byte-channels.
     * @return Readable source.
     */
    public static InputSource createInputSource(Supplier<InputStream> streamSupplier,
                                                Supplier<ReadableByteChannel> channelSupplier) {
        return DefaultInputSource.of(streamSupplier,channelSupplier);
    }

    /**
     * Creates an input source whose content can be read through an input-stream.
     * @param streamSupplier Factory of input-streams.
     * @return Readable source.
     */
    public static InputSource createInputSourceByStream(Supplier<InputStream> streamSupplier) {
        return InputStreamInputSource.of(streamSupplier);
    }

    /**
     * Creates an input source whose content can be read through a readable byte-channel.
     * @param channelSupplier Factory of readable byte-channels.
     * @return Readable source.
     */
    public static InputSource createInputSourceByChannel(Supplier<ReadableByteChannel> channelSupplier) {
        return ReadableByteChannelInputSource.of(channelSupplier);
    }

    /**
     * Creates an input source whose content can be read through a URL-connection.
     * @param connectionSupplier Factory of URL-connections.
     * @return Readable source.
     */
    public static InputSource createInputSourceByConnection(Supplier<URLConnection> connectionSupplier) {
        return createInputSourceByStream(() -> {
            try {
                URLConnection connection=connectionSupplier.get();
                return connection.getInputStream();
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create input-source from URL-connection!",ex);
            }
        });
    }

    /**
     * Creates an input source whose content is referenced by a URL.
     * @param url Reference to content.
     * @return Readable source.
     */
    public static InputSource createInputSource(URL url) {
        return createInputSourceByConnection(() -> {
            try {
                return url.openConnection();
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create input-source from URL; URL is '%s'!".formatted(url),ex);
            }
        });
    }

    /**
     * Creates an input source whose content is referenced by a URL.
     * @param url Reference to content.
     * @param proxy Connection proxy.
     * @return Readable source.
     */
    public static InputSource createInputSource(URL url,
                                                Proxy proxy) {
        return createInputSourceByConnection(() -> {
            try {
                return url.openConnection(proxy);
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create input-source from URL and proxy; URL is '%s', proxy is '%s'!".formatted(url,proxy),ex);
            }
        });
    }
}
