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

import com.yelstream.topp.standard.net.resource.identification.scheme.StandardScheme;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Standard URI predicate.
 * <p>
 *     This contains a predicate which tests for a recognizable feature on a concrete {@link URI} instance.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-16
 */
@AllArgsConstructor
@SuppressWarnings({"java:S115","LombokGetterMayBeUsed"})
public enum StandardURIPredicate {
    /**
     * Tests if the URI has a standard scheme as defined by {@link StandardScheme}.
     */
    HasStandardScheme(uri -> uri!=null && StandardScheme.match(uri)!=null),

    /**
     * Tests if the URI is relative and consists only of a path (no query, fragment, or authority).
     */
    IsPathOnly(uri -> uri!=null && !uri.isAbsolute() && uri.getPath()!=null && uri.getQuery()==null && uri.getFragment()==null && uri.getAuthority()==null),

    /**
     * Tests if the URI is opaque (not hierarchical, e.g., mailto:user@example.com).
     */
    IsOpaque(uri -> uri!=null && uri.isOpaque()),

    /**
     * Tests if the URI is hierarchical (not opaque).
     */
    IsHierarchical(uri -> uri!=null && !uri.isOpaque()),

    /**
     * Tests if the URI is absolute (not relative, has a scheme).
     */
    IsAbsolute(uri -> uri!=null && uri.isAbsolute()),

    /**
     * Tests if the URI is relative (not absolute, has no scheme).
     */
    IsRelative(uri -> uri!=null && !uri.isAbsolute()),

    /**
     * Tests if the URI has a valid authority (e.g., host or user info).
     */
    HasAuthority(uri -> uri!=null && !uri.isOpaque() && uri.getAuthority()!=null),

    /**
     * Tests if the URI has a non-empty host.
     */
    HasValidHost(uri -> uri!=null && !uri.isOpaque() && uri.getHost()!=null && !uri.getHost().isEmpty()),

    /**
     * Tests if the URI has an entry, contains {@code !/}, essentially indicating a JAR URL.
     */
    HasEntrySeparator(uri -> uri!=null && uri.getSchemeSpecificPart()!=null && uri.getSchemeSpecificPart().contains("!/")),

    /**
     * Tests if the URI has properties, contains {@code ;} (e.g., as in a {@code jdbc} URL).
     * <p>
     *     Examples:
     *     <ul>
     *         <li>{@code jdbc:sqlserver://localhost:1433} (opaque, non-standard scheme)</li>
     *         <li>{@code sqlserver://localhost:1433;databaseName=database1} (hierarchical, non-standard scheme and authority)</li>
     *     </ul>
     * </p>
     */
    HasPropertySeparator(uri -> uri != null  && uri.getSchemeSpecificPart()!=null && uri.getSchemeSpecificPart().contains(";")),

    /**
     * Tests if the URI has a path containing a colon, indicating a tag (e.g., {@code :latest}, {@code :1.0.0}), as found in docker URIs.
     */
    IsPathTagged(uri -> uri!=null && uri.getPath()!=null && !uri.getPath().isEmpty() && uri.getPath().contains(":")),

    /**
     * Tests if the URI is not regular, has a non-standard scheme construction.
     */
    IsNonRegular(HasEntrySeparator.predicate.or(HasPropertySeparator.predicate).or(IsPathTagged.predicate));

    /**
     * Predicate.
     * <p>
     *     Tests for a recognizable feature on a concrete {@link URI} instance.
     * </p>
     */
    @Getter
    private final Predicate<URI> predicate;

    /**
     * Indicates, if a URI is matched.
     * @param uri Matched URI.
     * @return Indicates match.
     */
    public boolean matches(URI uri) {
        Objects.requireNonNull(uri);
        return predicate.test(uri);
    }

    /**
     * Requires that a URI is matched.
     * @param uri Matched URI.
     * @throws IllegalArgumentException Thrown in case of illegal argument.
     */
    public void requireMatch(URI uri) {
        if (!matches(uri)) {
            throw new IllegalArgumentException("Failure to verify URI predicate; predicate is '%s', URI is '%s'!".formatted(this.name(),uri));
        }
    }

    /**
     * Streams all values.
     * @return Stream of all values.
     */
    public static Stream<StandardURIPredicate> streamValues() {
        return Arrays.stream(values());
    }

    /**
     * Streams all predicates.
     * @return Stream of all predicates.
     */
    public static Stream<Predicate<URI>> streamByPredicate() {
        return streamValues().map(StandardURIPredicate::getPredicate);
    }

    /**
     * Finds the values matching a URI.
     * @param uri URI to match.
     * @return Matched values.
     *         This may be {@code null}.
     */
    public static List<StandardURIPredicate> match(URI uri) {
        Objects.requireNonNull(uri);
        return streamValues().filter(e->e.matches(uri)).toList();
    }
}
