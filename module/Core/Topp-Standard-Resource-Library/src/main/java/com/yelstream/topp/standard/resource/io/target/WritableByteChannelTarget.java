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
 * Target based on access to {@link WritableByteChannel}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-02
 */
@AllArgsConstructor(staticName="of")
final class WritableByteChannelTarget implements Target {
    /**
     * Supplier of writable byte-channels.
     * <p>
     *     Note that usages catch {@link UncheckedIOException}.
     * </p>
     */
    private final Supplier<WritableByteChannel> channelSupplier;

    @Override
    public OutputStream openStream() throws IOException {
        WritableByteChannel channel=openChannel();
        return channel==null?null:Channels.newOutputStream(channel);
    }

    @Override
    public WritableByteChannel openChannel() throws IOException {
        try {
            return channelSupplier.get();
        } catch (UncheckedIOException ex) {
            throw new IOException("Failure to create channel!",ex);
        }
    }
}
