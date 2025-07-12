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
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
public interface Source extends AnySource<InputStream,ReadableByteChannel>, StreamSource<InputStream>, ChannelSource<ReadableByteChannel> {
    /**
     * Creates a new stream to read data.
     * @return Stream to read data.
     * @throws IOException Thrown in case of I/O error.
     */
    @Override
    InputStream openStream() throws IOException;

    /**
     * Creates a new channel to read data.
     * @return Channel to read data.
     * @throws IOException Thrown in case of I/O error.
     */
    @Override
    ReadableByteChannel openChannel() throws IOException;
}
