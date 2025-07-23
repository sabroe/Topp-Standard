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

package com.yelstream.topp.standard.net.resource.identification.scheme;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Standard, named scheme.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
@AllArgsConstructor
@SuppressWarnings({"java:S115","LombokGetterMayBeUsed"})
public enum StandardScheme {
    File(Scheme.of("file")),
    JAR(Scheme.of("jar")),
    HTTP(Scheme.of("http")),  //TO-DO: Remove this; is not built-in!
    HTTPS(Scheme.of("https"));  //TO-DO: Remove this; is not built-in!

    /**
     * Scheme.
     */
    @Getter
    private final Scheme scheme;

    /**
     * Indicates, if a URI is matched.
     * @param uri Matched URI.
     * @return Indicates match.
     */
    public boolean matches(URI uri) {
        return scheme.matches(uri);
    }

    /**
     * Requires that a URI is matched.
     * @param uri Matched URI.
     * @throws IllegalArgumentException Thrown in case of illegal argument.
     */
    public void requireMatch(URI uri) {
        scheme.requireMatch(uri);
    }

    /**
     * Streams all values.
     * @return Stream of all values.
     */
    public static Stream<StandardScheme> streamValues() {
        return Arrays.stream(values());
    }

    /**
     * Streams all schemes.
     * @return Stream of all schemes.
     */
    public static Stream<Scheme> streamByScheme() {
        return streamValues().map(StandardScheme::getScheme);
    }

    /**
     * Finds the value matching a URI.
     * @param uri URI to match.
     * @return Matched valued.
     *         This may be {@code null}.
     */
    public static StandardScheme match(URI uri) {
        Objects.requireNonNull(uri);
        return streamValues().filter(e->e.matches(uri)).findFirst().orElse(null);
    }
}
