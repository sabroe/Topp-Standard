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

package com.yelstream.topp.standard.net.resource.identification.build;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Constructs a URI from a unified set of arguments.
 * <p>
 *     This is used as the action part in the construction of {@link URI} instances.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-15
 */
@FunctionalInterface
public interface URIConstructor {
    /**
     * Constructs a URI.
     * @param argument Unified set of arguments.
     *                 This may use the individual arguments in a selective manner.
     * @return Created URI.
     * @throws URISyntaxException Thrown in case of syntax error.
     */
    URI construct(URIArgument argument) throws URISyntaxException;

    /**
     * Constructs a URI.
     * @param argument Unified set of arguments.
     *                 This may use the individual arguments in a selective manner.
     * @return Created URI.
     */
    default URI create(URIArgument argument) {
        try {
            return construct(argument);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Failure to create URI; set of arguments are '%s'!".formatted(argument),ex);
        }
    }
}
