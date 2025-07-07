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

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * Factory of URL-handlers for a single, known protocol.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
public interface NamedURLStreamHandlerFactory extends URLStreamHandlerFactory {
    /**
     * Gets the protocol name.
     * @return Protocol name.
     */
    String getProtocol();

    /**
     * Creates a handler for the protocol.
     * @return Created handler.
     */
    default URLStreamHandler createURLStreamHandler() {
        return createURLStreamHandler(getProtocol());
    }
}
