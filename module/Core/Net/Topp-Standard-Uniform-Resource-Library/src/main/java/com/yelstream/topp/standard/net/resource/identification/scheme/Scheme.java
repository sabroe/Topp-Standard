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
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * URI scheme name.
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
public final class Scheme {
    /**
     * Scheme name.
     */
    @Getter
    private final String name;

    public boolean matches(URI uri) {
        return name.equalsIgnoreCase(uri.getScheme());
    }

    public void requireMatch(URI uri) {
        if (!matches(uri)) {
            throw new IllegalArgumentException("Failure to verify URI scheme; URI is '%s'!".formatted(uri));
        }
    }

    public URL toURL(URI uri) {
        requireMatch(uri);
        try {
            return uri.toURL();
        } catch (MalformedURLException ex) {
            throw new IllegalStateException("Failure to convert URI to URL; actual URI is '%s'!".formatted(uri));
        }
    }

    public static Scheme of(URI uri) {
        return of(uri.getScheme());
    }
}
