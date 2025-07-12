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

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.function.Supplier;

/**
 * Default implementation of {@link Target}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-02
 */
@AllArgsConstructor(staticName="of")
final class DefaultTarget implements Target {
    /**
     * Supplier of output-streams.
     * <p>
     *     Note that usages catch {@link UncheckedIOException}.
     * </p>
     */
    private final Supplier<OutputStream> streamSupplier;

    /**
     * Supplier of writable byte-channels.
     * <p>
     *     Note that usages catch {@link UncheckedIOException}.
     * </p>
     */
    private final Supplier<WritableByteChannel> channelSupplier;

    @Override
    public OutputStream openStream() throws IOException {
        try {
            return streamSupplier.get();
        } catch (UncheckedIOException ex) {
            throw new IOException("Failure to create stream!",ex);
        }
    }

    @Override
    public WritableByteChannel openChannel() throws IOException {
        try {
            return channelSupplier.get();
        } catch (UncheckedIOException ex) {
            throw new IOException("Failure to create channel!",ex);
        }
    }

    public static DefaultTarget ofStream(Supplier<OutputStream> streamSupplier) {
        Supplier<WritableByteChannel> channelSupplier=()->{
            OutputStream stream=streamSupplier.get();
            return stream==null?null: Channels.newChannel(stream);
        };
        return of(streamSupplier,channelSupplier);
    }

    public static DefaultTarget ofChannel(Supplier<WritableByteChannel> channelSupplier) {
        Supplier<OutputStream> streamSupplier=()->{
            WritableByteChannel channel=channelSupplier.get();
            return channel==null?null:Channels.newOutputStream(channel);
        };
        return of(streamSupplier,channelSupplier);
    }
}
