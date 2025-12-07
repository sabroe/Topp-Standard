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

package com.yelstream.topp.standard.net.resource.identification.handler.factory;

import com.yelstream.topp.standard.net.resource.identification.handler.URISchemeHandler;

/**
 * Factory of URI-handlers for a single, known scheme.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-21
 */
public interface NamedURISchemeHandlerFactory extends URISchemeHandlerFactory {
    /**
     * Gets the scheme name.
     * @return Scheme name.
     */
    String getScheme();

    /**
     * Creates a handler for the scheme.
     * @return Created handler.
     */
    default URISchemeHandler createURISchemeHandler() {
        return createURISchemeHandler(getScheme());
    }
}
