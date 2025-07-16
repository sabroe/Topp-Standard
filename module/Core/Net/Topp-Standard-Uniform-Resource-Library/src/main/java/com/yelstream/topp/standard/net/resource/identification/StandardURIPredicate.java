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

package com.yelstream.topp.standard.net.resource.identification;

import com.yelstream.topp.standard.net.resource.identification.scheme.StandardScheme;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Standard URI predicate.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-16
 */
@AllArgsConstructor
@SuppressWarnings({"java:S115","LombokGetterMayBeUsed"})
public enum StandardURIPredicate {
    /**
     * Tests if the URI has a standard scheme as defined by StandardScheme.
     */
    HasStandardScheme(uri -> uri != null && StandardScheme.match(uri) != null),

    /**
     * Tests if the URI is relative and consists only of a path (no query, fragment, or authority).
     */
    IsPathOnly(uri -> uri != null && !uri.isAbsolute() && uri.getPath() != null && uri.getQuery() == null && uri.getFragment() == null && uri.getAuthority() == null),

    /**
     * Tests if the URI is opaque (e.g., mailto:user@example.com).
     */
    IsOpaque(uri -> uri != null && uri.isOpaque()),

    /**
     * Tests if the URI is hierarchical (not opaque).
     */
    IsHierarchical(uri -> uri != null && !uri.isOpaque()),

    /**
     * Tests if the URI is absolute (has a scheme).
     */
    IsAbsolute(uri -> uri != null && uri.isAbsolute()),

    /**
     * Tests if the URI is relative (no scheme).
     */
    IsRelative(uri -> uri != null && !uri.isAbsolute()),

/*
    */
/**
     * Tests if the URI has a standard scheme and is considered regular.
     *//*

    IsRegular(uri -> uri != null && StandardScheme.match(uri) != null),
*/

/*
    */
/**
     * Tests if the URI has a non-standard scheme (e.g., jar:, jdbc:).
     *//*

    IsNonRegular(uri -> uri != null && StandardScheme.match(uri) == null),
*/

    /**
     * Tests if the URI has a valid authority (e.g., host or user info).
     */
    HasAuthority(uri -> uri != null && !uri.isOpaque() && uri.getAuthority() != null),

    /**
     * Tests if the URI has a non-empty host.
     */
    HasValidHost(uri -> uri != null && !uri.isOpaque() && uri.getHost() != null && !uri.getHost().isEmpty());

    @Getter
    private final Predicate<URI> predicate;

    public boolean matches(URI uri) {
        Objects.requireNonNull(uri);
        return predicate.test(uri);
    }

    public void requireMatch(URI uri) {
        if (!matches(uri)) {
            throw new IllegalArgumentException("Failure to verify URI predicate; predicate is '%s', URI is '%s'!".formatted(this.name(),uri));
        }
    }

    public static Stream<StandardURIPredicate> streamValues() {
        return Arrays.stream(values());
    }

    public static Stream<Predicate<URI>> streamByPredicate(URI uri) {
        Objects.requireNonNull(uri);
        return streamValues().map(StandardURIPredicate::getPredicate);
    }
}
