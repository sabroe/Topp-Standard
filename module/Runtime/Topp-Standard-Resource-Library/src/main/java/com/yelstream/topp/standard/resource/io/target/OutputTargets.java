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

package com.yelstream.topp.standard.resource.io.target;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.WritableByteChannel;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link OutputTarget}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-02
 */
@UtilityClass
public class OutputTargets {
    /**
     * Creates an output target.
     * @param streamSupplier Factory of output-streams.
     * @param channelSupplier Factory of writable byte-channels.
     * @return Writable target.
     */
    public static OutputTarget createOutputTarget(Supplier<OutputStream> streamSupplier,
                                                  Supplier<WritableByteChannel> channelSupplier) {
        return DefaultOutputTarget.of(streamSupplier,channelSupplier);
    }

    /**
     * Creates an output target whose content can be written through an output-stream.
     * @param streamSupplier Factory of output-streams.
     * @return Writable target.
     */
    public static OutputTarget createOutputTargetByStream(Supplier<OutputStream> streamSupplier) {
        return OutputStreamOutputTarget.of(streamSupplier);
    }

    /**
     * Creates an output target whose content can be written through a readable byte-channel.
     * @param channelSupplier Factory of readable byte-channels.
     * @return Readable source.
     */
    public static OutputTarget createOutputTargetByChannel(Supplier<WritableByteChannel> channelSupplier) {
        return WritableByteChannelOutputTarget.of(channelSupplier);
    }

    /**
     * Creates an output target whose content can be written through a URL-connection.
     * @param connectionSupplier Factory of URL-connections.
     * @return Readable source.
     */
    public static OutputTarget createOutputTargetByConnection(Supplier<URLConnection> connectionSupplier) {
        return createOutputTargetByStream(() -> {
            try {
                URLConnection connection=connectionSupplier.get();
                return connection.getOutputStream();
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create output-target from URL-connection!",ex);
            }
        });
    }

    /**
     * Creates an output target whose content is referenced by a URL.
     * @param url Reference to content.
     * @return Readable source.
     */
    public static OutputTarget createOutputTarget(URL url) {
        return createOutputTargetByConnection(() -> {
            try {
                return url.openConnection();
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create output-target from URL; URL is '%s'!".formatted(url),ex);
            }
        });
    }

    /**
     * Creates an output target whose content is referenced by a URL.
     * @param url Reference to content.
     * @param proxy Connection proxy.
     * @return Readable source.
     */
    public static OutputTarget createOutputTarget(URL url,
                                                  Proxy proxy) {
        return createOutputTargetByConnection(() -> {
            try {
                return url.openConnection(proxy);
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create output-target from URL and proxy; URL is '%s', proxy is '%s'!".formatted(url,proxy),ex);
            }
        });
    }
}
