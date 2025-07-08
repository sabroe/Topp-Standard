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

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URLStreamHandler;
import java.util.function.Supplier;

/**
 * Simple factory of handlers for known protocols.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
@AllArgsConstructor(staticName="of")
final class SimpleURLStreamHandlerFactory implements NamedURLStreamHandlerFactory {
    /**
     * Protocol name.
     */
    @Getter
    private final String protocol;

    /**
     * Factory of handlers;
     */
    private final Supplier<URLStreamHandler> handlerSupplier;

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (!this.protocol.equals(protocol)) {
            return null;
        }
        return handlerSupplier.get();
    }
}
