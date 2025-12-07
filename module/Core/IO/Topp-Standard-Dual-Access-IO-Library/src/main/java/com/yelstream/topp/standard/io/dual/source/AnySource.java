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

package com.yelstream.topp.standard.io.dual.source;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

/**
 * Source of readable, binary input data.
 * <p>
 *     Note that {@link #openStream()} and {@link #openChannel()} represent different access methods,
 *     hence only one of these are to be used at a time.
 * </p>
 * <p>
 *     This is a dual-access interface.
 * </p>
 * @param <S> Type of input-stream.
 * @param <C> Type of readable byte-channel.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
public interface AnySource<S extends InputStream,C extends ReadableByteChannel> extends BaseSource<S,C> {
    /**
     * Creates a new stream to read data.
     * @return Stream to read data.
     * @throws IOException Thrown in case of I/O error.
     */
    @Override
    S openStream() throws IOException;

    /**
     * Creates a new channel to read data.
     * @return Channel to read data.
     * @throws IOException Thrown in case of I/O error.
     */
    @Override
    C openChannel() throws IOException;
}
