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

package com.yelstream.topp.standard.net.resource.location.handler.factory;

import com.yelstream.topp.standard.system.load.instance.InstanceLoader;
import com.yelstream.topp.standard.system.load.instance.InstanceLoaders;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Utilities addressing instances of {@link URLStreamHandlerFactory}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
@Slf4j
@UtilityClass
public class URLStreamHandlerFactories {
    /**
     * Loader and registry of all known URL stream handler factories.
     * <p>
     *     Note that it is possible to address the suppliers of this registry, eventually reset the cached entries.
     * </p>
     */
    @Getter
    private static final InstanceLoader<URLStreamHandlerFactory> loader=InstanceLoaders.forClass(URLStreamHandlerFactory.class);

    /**
     * Creates a new {@code URLStreamHandler} instance with the specified protocol.
     * @param protocol The protocol ("{@code ftp}", "{@code http}", "{@code nntp}", etc.).
     * @return A {@code URLStreamHandler} for the specific protocol,
     *         or {@code null} if this factory cannot create a handler for the specific protocol.
     * @see java.net.URLStreamHandlerFactory
     */
    public static URLStreamHandler createURLStreamHandler(String protocol) {
        return loader.getInstances().stream().map(factory->factory.createURLStreamHandler(protocol)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    /**
     * Creates a URL stream handler factory wrapping all registered factories.
     * @return Factory wrapping all registered factories.
     */
    public static URLStreamHandlerFactory createURLStreamHandlerFactory() {
        return ChainURLStreamHandlerFactory.of(loader.getInstances());
    }

    /**
     * Creates a factory for a handler of a known protocol.
     * @param protocol Protocol.
     * @param handlerSupplier Factory of handlers for the protocol-.
     * @return Created factory.
     */
    public static NamedURLStreamHandlerFactory createNamedURLStreamHandlerFactory(String protocol,
                                                                                  Supplier<URLStreamHandler> handlerSupplier) {
        return SimpleURLStreamHandlerFactory.of(protocol,handlerSupplier);
    }

    /**
     *
     */
    @SuppressWarnings("java:S3011")
    public static URLStreamHandlerFactory getGlobalURLStreamHandlerFactory() throws IllegalStateException {  //TO-DO: Consider removing this; do not really need this hacker-method!
        try {
            Field factoryField=URL.class.getDeclaredField("factory");
            factoryField.setAccessible(true);
            return (URLStreamHandlerFactory) factoryField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new IllegalStateException("Failure to obtain global factory!",ex);
        }
    }
}
