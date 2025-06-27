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

package com.yelstream.topp.standard.load.io;

import com.yelstream.topp.standard.load.resource.lookup.ResourceLookup;
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
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@UtilityClass
public class InputSources {

    public static InputSource createInputSource(ClassLoader classLoader,
                                                String name) {
        return createInputSourceByStream(()->classLoader.getResourceAsStream(name));
    }

    public static InputSource createInputSource(ResourceLookup lookup,
                                                String name) {
        return createInputSourceByStream(()->lookup.getResourceAsStream(name));
    }

    public static InputSource createInputSourceByStream(Supplier<InputStream> streamSupplier) {
        return InputStreamInputSource.of(streamSupplier);
    }

    public static InputSource createInputSourceByChannel(Supplier<ReadableByteChannel> channelSupplier) {
        return ReadableByteChannelInputSource.of(channelSupplier);
    }

    public static InputSource createInputSourceByConnection(Supplier<URLConnection> connectionSupplier) {
        return createInputSourceByStream(() -> {
            try {
                return connectionSupplier.get().getInputStream();
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create input-source from URL-connection!",ex);
            }
        });
    }

    public static InputSource createInputSource(URL url) {
        return createInputSourceByConnection(() -> {
            try {
                return url.openConnection();
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create input-source from URL!",ex);
            }
        });
    }

    public static InputSource createInputSource(URL url,
                                                Proxy proxy) {
        return createInputSourceByConnection(() -> {
            try {
                return url.openConnection(proxy);
            } catch (IOException ex) {
                throw new UncheckedIOException("Failure to create input-source from URL and proxy!",ex);
            }
        });
    }
}
