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
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Named protocol.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
@SuppressWarnings("LombokGetterMayBeUsed")
@EqualsAndHashCode
@AllArgsConstructor(staticName="of")
public final class Protocol {
    /**
     * Protocol name.
     */
    @Getter
    private final String name;

    /**
     * Indicates, if a URL is matched.
     * @param url Matched URL.
     * @return Indicates match.
     */
    public boolean matches(URL url) {
        return name.equalsIgnoreCase(url.getProtocol());
    }

    /**
     * Requires that a URL is matched.
     * @param url Matched URL.
     * @throws IllegalArgumentException Thrown in case of illegal argument.
     */
    public void requireMatch(URL url) {
        if (!matches(url)) {
            throw new IllegalArgumentException("Failure to verify URL protocol; URL is '%s'!".formatted(url));
        }
    }

    /**
     * Creates a URI from a URL while requiring that the URL is matched.
     * @param url URL.
     * @return Created URI.
     * @throws IllegalStateException Thrown in case of illegal argument.
     */
    public URI toURI(URL url) {
        requireMatch(url);
        try {
            return url.toURI();
        } catch (URISyntaxException ex) {
            throw new IllegalStateException("Failure to convert URL to URI; actual URL is '%s'!".formatted(url));
        }
    }

    /**
     * Creates a protocol from a URL.
     * @param url URL.
     * @return Created protocol.
     */
    public static Protocol of(URL url) {
        return of(url.getProtocol());
    }
}
