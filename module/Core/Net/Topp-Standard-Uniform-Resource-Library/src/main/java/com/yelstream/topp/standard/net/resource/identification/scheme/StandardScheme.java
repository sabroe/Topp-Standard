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
import java.util.stream.Stream;

/**
 * Standard schemes.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
@AllArgsConstructor
@SuppressWarnings({"java:S115","LombokGetterMayBeUsed"})
public enum StandardScheme {
    File(Scheme.of("file")),
    JAR(Scheme.of("jar")),
    HTTP(Scheme.of("http")),
    HTTPS(Scheme.of("https"));

    @Getter
    private final Scheme scheme;

    public boolean matches(URI uri) {
        return scheme.matches(uri);
    }

    public void requireMatch(URI uri) {
        scheme.requireMatch(uri);
    }

    public static Stream<StandardScheme> streamValues() {
        return Arrays.stream(values());
    }

    public static Stream<Scheme> streamByScheme() {
        return streamValues().map(StandardScheme::getScheme);
    }

    public static StandardScheme match(URI uri) {
        return streamValues().filter(e->e.matches(uri)).findFirst().orElse(null);
    }
}
