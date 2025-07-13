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

package com.yelstream.topp.standard.net.resource.location;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;
import java.net.URL;

/**
 * URL protocol name.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
@SuppressWarnings("LombokGetterMayBeUsed")
@AllArgsConstructor(staticName="of")
public final class Protocol {
    /**
     * Protocol name.
     */
    @Getter
    private final String name;

    public boolean matches(URL url) {
        return name.equalsIgnoreCase(url.getProtocol());
    }

    public void requireMatch(URL url) {
        if (!matches(url)) {
            throw new IllegalArgumentException("Failure to verify URL protocol; URL is '%s'!".formatted(url));
        }
    }

    public URI toURI(URL url) {
        requireMatch(url);
        return URLs.toURI(url);
    }
}
