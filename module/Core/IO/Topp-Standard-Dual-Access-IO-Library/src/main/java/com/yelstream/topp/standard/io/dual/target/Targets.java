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

package com.yelstream.topp.standard.io.dual.target;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Pipe;
import java.nio.channels.WritableByteChannel;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link Target}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-02
 */
@UtilityClass
public class Targets {
    /**
     * Creates a target.
     * @param streamSupplier Factory of output-streams.
     * @param channelSupplier Factory of writable byte-channels.
     * @return Writable target.
     */
    public static Target createTarget(Supplier<OutputStream> streamSupplier,
                                      Supplier<WritableByteChannel> channelSupplier) {
        return DefaultTarget.of(streamSupplier,channelSupplier);
    }

    /**
     * Creates a target whose content can be written through an output-stream.
     * @param streamSupplier Factory of output-streams.
     * @return Writable target.
     */
    public static Target createTargetByStream(Supplier<OutputStream> streamSupplier) {
        return DefaultTarget.ofStream(streamSupplier);
    }

    /**
     * Creates a target whose content can be written through a readable byte-channel.
     * @param channelSupplier Factory of readable byte-channels.
     * @return Writable target.
     */
    public static Target createTargetByChannel(Supplier<WritableByteChannel> channelSupplier) {
        return DefaultTarget.ofChannel(channelSupplier);
    }

    /**
     * Creates a target.
     * @param streamSupplier Factory of output-streams.
     * @param channelSupplier Factory of writable byte-channels.
     * @return Writable target.
     * @param <S> Type of output-stream.
     * @param <C> Type of writable byte-channel.
     */
    public static <S extends OutputStream,C extends WritableByteChannel> AnyTarget<S,C> createAnyTarget(Supplier<S> streamSupplier,
                                                                                                        Supplier<C> channelSupplier) {
        return DefaultAnyTarget.of(streamSupplier,channelSupplier);
    }

    /**
     * Creates a target whose content can be written through an output-stream.
     * @param streamSupplier Factory of output-streams.
     * @return Writable target.
     * @param <S> Type of output-stream.
     */
    public static <S extends OutputStream> StreamTarget<S> createStreamTarget(Supplier<S> streamSupplier) {
        return DefaultStreamTarget.of(streamSupplier);
    }

    /**
     * Creates a target whose content can be written through a writable byte-channel.
     * @param channelSupplier Factory of writable byte-channels.
     * @return Writable target.
     * @param <C> Type of writable byte-channel.
     */
    public static <C extends WritableByteChannel> ChannelTarget<C> createChannelTarget(Supplier<C> channelSupplier) {
        return DefaultChannelTarget.of(channelSupplier);
    }

    /**
     * Creates a target whose content can be written through a pipe.
     * @param pipe Pipe.
     * @return Writable target.
     */
    public static ChannelTarget<Pipe.SinkChannel> createChannelTarget(Pipe pipe) {
        return createChannelTarget(pipe::sink);
    }

    /**
     * Creates a target whose content can be written through a URL-connection.
     * @param connectionSupplier Factory of URL-connections.
     * @return Writable target.
     */
    public static Target createTargetByConnection(Supplier<URLConnection> connectionSupplier) {
        return createTargetByStream(() -> {
            try {
                URLConnection connection=connectionSupplier.get();
                return connection.getOutputStream();
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create target from URL-connection!",ex);
            }
        });
    }

    /**
     * Creates a target whose content is referenced by a URL.
     * @param url Reference to content.
     * @return Writable target.
     */
    public static Target createTarget(URL url) {
        return createTargetByConnection(() -> {
            try {
                return url.openConnection();
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create target from URL; URL is '%s'!".formatted(url),ex);
            }
        });
    }

    /**
     * Creates a target whose content is referenced by a URL.
     * @param url Reference to content.
     * @param proxy Connection proxy.
     * @return Writable target.
     */
    public static Target createTarget(URL url,
                                      Proxy proxy) {
        return createTargetByConnection(() -> {
            try {
                return url.openConnection(proxy);
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create target from URL and proxy; URL is '%s', proxy is '%s'!".formatted(url,proxy),ex);
            }
        });
    }
}
