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

package com.yelstream.topp.standard.net.resource.identification.util;

import lombok.experimental.UtilityClass;

import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Utility addressing instances of {@link URIConstructor}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-15
 */
@UtilityClass
public class URIConstructors {
    /**
     * Selects a URI constructor by trial and error.
     * <p>
     *     Tries each constructor in {@link StandardURIConstructor} until one succeeds.
     * </p>
     * @param arguments The URI arguments to test.
     * @return The first {@link URIConstructor} that successfully constructs a URI, or null if none succeed.
     */
    public static URIConstructor selectByTrial(URIArgument arguments) {
        Objects.requireNonNull(arguments);
        for (StandardURIConstructor constructor : StandardURIConstructor.values()) {
            try {
                constructor.construct(arguments);
                return constructor::construct;
            } catch (URISyntaxException ex) {
                //Empty!
                //TO-DO: Consider logging this. SLF4J!
            }
        }
        return null;
    }

    /**
     * Selects a URI constructor based on the provided arguments.
     * <p>
     *     Uses predicates defined in {@link }StandardURIConstructor} to choose the best-fit constructor.
     * </p>
     * @param arguments The URI arguments to analyze.
     * @return The best-fit {@link URIConstructor}, or null if no suitable constructor is found.
     */
    public static URIConstructor selectByApplicability(URIArgument arguments) {
        Objects.requireNonNull(arguments);
        for (StandardURIConstructor constructor : StandardURIConstructor.values()) {
            if (constructor.isApplicable(arguments)) {
                return constructor::construct;
            }
        }
        return null;
    }
}
