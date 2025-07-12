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

package com.yelstream.topp.standard.io.dual.source;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Supplier;

/**
 * Default implementation of {@link AnySource}.
 * @param <S> Type of input-stream.
 * @param <C> Type of readable byte-channel.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
@AllArgsConstructor(staticName="of")
final class DefaultAnySource<S extends InputStream, C extends ReadableByteChannel> implements AnySource<S,C> {
    /**
     * Supplier of input-streams.
     * <p>
     *     Note that usages catch {@link UncheckedIOException}.
     * </p>
     */
    private final Supplier<S> streamSupplier;

    /**
     * Supplier of readable byte-channels.
     * <p>
     *     Note that usages catch {@link UncheckedIOException}.
     * </p>
     */
    private final Supplier<C> channelSupplier;

    @Override
    public S openStream() throws IOException {
        try {
            return streamSupplier.get();
        } catch (UncheckedIOException ex) {
            throw new IOException("Failure to create stream!",ex);
        }
    }

    @Override
    public C openChannel() throws IOException {
        try {
            return channelSupplier.get();
        } catch (UncheckedIOException ex) {
            throw new IOException("Failure to create channel!",ex);
        }
    }
}
