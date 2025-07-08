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

package com.yelstream.topp.standard.resource.net.handler.factory;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.function.Supplier;
import java.net.URLStreamHandlerFactory;

/**
 * Utilities addressing instances of {@link URLStreamHandlerFactory}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
@UtilityClass
public class URLStreamHandlerFactories {
    /**
     * Creates a factory for a handler of a known protocol.
     * @param protocol Protocol.
     * @param handlerSupplier Factory of handlers for the protocol-.
     * @return Created factory.
     */
    public static NamedURLStreamHandlerFactory createURLStreamHandlerFactory(String protocol,
                                                                      Supplier<URLStreamHandler> handlerSupplier) {
        return SimpleURLStreamHandlerFactory.of(protocol,handlerSupplier);
    }

    /**
     *
     * @return
     * @throws IllegalStateException
     */
    @SuppressWarnings("java:S3011")
    public static URLStreamHandlerFactory getGlobalURLStreamHandlerFactory() throws IllegalStateException {
        try {
            Field factoryField=URL.class.getDeclaredField("factory");
            factoryField.setAccessible(true);
            return (URLStreamHandlerFactory) factoryField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new IllegalStateException("Failure to obtain global factory!",ex);
        }
    }
}
