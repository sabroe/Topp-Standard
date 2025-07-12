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
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link Source}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@UtilityClass
public class Sources {
    /**
     * Creates a source.
     * @param streamSupplier Factory of input-streams.
     * @param channelSupplier Factory of readable byte-channels.
     * @return Readable source.
     */
    public static Source createSource(Supplier<InputStream> streamSupplier,
                                      Supplier<ReadableByteChannel> channelSupplier) {
        return DefaultSource.of(streamSupplier,channelSupplier);
    }

    /**
     * Creates a source whose content can be read through an input-stream.
     * @param streamSupplier Factory of input-streams.
     * @return Readable source.
     */
    public static Source createSourceByStream(Supplier<InputStream> streamSupplier) {
        return DefaultSource.ofStream(streamSupplier);
    }

    /**
     * Creates a source whose content can be read through a writable byte-channel.
     * @param channelSupplier Factory of readable byte-channels.
     * @return Readable source.
     */
    public static Source createSourceByChannel(Supplier<ReadableByteChannel> channelSupplier) {
        return DefaultSource.ofChannel(channelSupplier);
    }

    /**
     * Creates a source.
     * @param streamSupplier Factory of input-streams.
     * @param channelSupplier Factory of readable byte-channels.
     * @return Readable source.
     * @param <S> Type of input-stream.
     * @param <C> Type of readable byte-channel.
     */
    public static <S extends InputStream,C extends ReadableByteChannel> AnySource<S,C> createAnySource(Supplier<S> streamSupplier,
                                                                                                       Supplier<C> channelSupplier) {
        return DefaultAnySource.of(streamSupplier,channelSupplier);
    }

    /**
     * Creates a source whose content can be read through an input-stream.
     * @param streamSupplier Factory of input-streams.
     * @return Readable source.
     * @param <S> Type of input-stream.
     */
    public static <S extends InputStream> StreamSource<S> createStreamSource(Supplier<S> streamSupplier) {
        return DefaultStreamSource.of(streamSupplier);
    }

    /**
     * Creates a source whose content can be read through a readable byte-channel.
     * @param channelSupplier Factory of readable byte-channels.
     * @return Readable source.
     * @param <C> Type of readable byte-channel.
     */
    public static <C extends ReadableByteChannel> ChannelSource<C> createChannelSource(Supplier<C> channelSupplier) {
        return DefaultChannelSource.of(channelSupplier);
    }

    /**
     * Creates a source whose content can be read through a pipe.
     * @param pipe Pipe.
     * @return Readable source.
     */
    public static ChannelSource<Pipe.SourceChannel> createChannelSource(Pipe pipe) {
        return createChannelSource(pipe::source);
    }

    /**
     * Creates a source whose content can be read through a URL-connection.
     * @param connectionSupplier Factory of URL-connections.
     * @return Readable source.
     */
    public static Source createSourceByConnection(Supplier<URLConnection> connectionSupplier) {
        return createSourceByStream(() -> {
            try {
                URLConnection connection=connectionSupplier.get();
                return connection.getInputStream();
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create source from URL-connection!",ex);
            }
        });
    }

    /**
     * Creates a source whose content is referenced by a URL.
     * @param url Reference to content.
     * @return Readable source.
     */
    public static Source createSource(URL url) {
        return createSourceByConnection(() -> {
            try {
                return url.openConnection();
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create source from URL; URL is '%s'!".formatted(url),ex);
            }
        });
    }

    /**
     * Creates a source whose content is referenced by a URL.
     * @param url Reference to content.
     * @param proxy Connection proxy.
     * @return Readable source.
     */
    public static Source createSource(URL url,
                                      Proxy proxy) {
        return createSourceByConnection(() -> {
            try {
                return url.openConnection(proxy);
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create source from URL and proxy; URL is '%s', proxy is '%s'!".formatted(url,proxy),ex);
            }
        });
    }
}
