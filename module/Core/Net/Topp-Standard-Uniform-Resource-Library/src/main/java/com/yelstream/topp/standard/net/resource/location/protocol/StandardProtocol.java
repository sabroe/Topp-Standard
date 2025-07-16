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

package com.yelstream.topp.standard.net.resource.location.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URL;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Standard protocol.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
@AllArgsConstructor
@SuppressWarnings({"java:S115","LombokGetterMayBeUsed"})
public enum StandardProtocol {
    File(Protocol.of("file")),
    JAR(Protocol.of("jar"));

    @Getter
    private final Protocol protocol;

    public boolean matches(URL url) {
        return protocol.matches(url);
    }

    public void requireMatch(URL url) {
        protocol.requireMatch(url);
    }

    public static Stream<StandardProtocol> streamValues() {
        return Arrays.stream(values());
    }

    public static Stream<Protocol> streamByProtocol() {
        return streamValues().map(StandardProtocol::getProtocol);
    }

    public static StandardProtocol match(URL url) {
        return streamValues().filter(e->e.matches(url)).findFirst().orElse(null);
    }
}
