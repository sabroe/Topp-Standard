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
 * OTarget based on access to {@link OutputStream}.
 * <p>
 *     Note that this creates a {@link WritableByteChannel} from a {@link OutputStream}
 *     without applying additional buffering in between.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-02
 */
@AllArgsConstructor(staticName="of")
final class OutputStreamTarget implements Target {
    /**
     * Supplier of output-streams.
     * <p>
     *     Note that usages catch {@link UncheckedIOException}.
     * </p>
     */
    private final Supplier<OutputStream> streamSupplier;

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
        OutputStream stream=openStream();
        return stream==null?null:Channels.newChannel(stream);
    }
}
