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

package com.yelstream.topp.standard.net.resource.identification.build;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Utility addressing instances of {@link URIConstructor}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-15
 */
@Slf4j
@UtilityClass
public class URIConstructors {
    /**
     * Creates a stream of constructors operational upon a set of arguments.
     * <p>
     *     This is done by trial-and-error in a brute force manner.
     * </p>
     * @param argument Set of arguments.
     * @return Created stream.
     */
    public static Stream<URIConstructor> streamByTrial(URIArgument argument) {
        Objects.requireNonNull(argument);
        return StandardURIConstructor.streamValues().filter(e -> {
            try {
                URI uri=e.construct(argument);
                return uri!=null;
            } catch (URISyntaxException ex) {
                log.atDebug().setMessage("Failure to match standard constructor; standard constructor is '{}'!").addArgument(e.name()).setCause(ex).log();
                return false;
            }
        }).map(StandardURIConstructor::getConstructor);
    }

    /**
     * Selects a URI constructor by trial and error.
     * <p>
     *     Tries each constructor in {@link StandardURIConstructor} until one succeeds.
     * </p>
     * @param argument The URI arguments to test.
     * @return The first {@link URIConstructor} that successfully constructs a URI, or null if none succeed.
     */
    public static URIConstructor selectByTrial(URIArgument argument) {
        Objects.requireNonNull(argument);
        return streamByTrial(argument).findFirst().orElse(null);
    }

    /**
     * Creates a stream of constructors applicable upon a set of arguments.
     * <p>
     *     This is done by evaluation of the provided set of arguments.
     * </p>
     * @param argument Set of arguments.
     * @return Created stream.
     */
    public static Stream<URIConstructor> streamByApplicability(URIArgument argument) {
        Objects.requireNonNull(argument);
        return StandardURIConstructor.streamByApplicability(argument).map(StandardURIConstructor::getConstructor);
    }

    /**
     * Selects a URI constructor based on the provided arguments.
     * <p>
     *     Uses predicates defined in {@link StandardURIConstructor} to choose the best-fit constructor.
     * </p>
     * @param argument The URI arguments to analyze.
     * @return The best-fit {@link URIConstructor}, or null if no suitable constructor is found.
     */
    public static URIConstructor selectByApplicability(URIArgument argument) {
        Objects.requireNonNull(argument);
        return streamByApplicability(argument).findFirst().orElse(null);
    }
}
