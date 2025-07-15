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

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-15
 */
@AllArgsConstructor
@SuppressWarnings({"java:S115","LombokGetterMayBeUsed"})
public enum StandardURIConstructor {
    /**
     * Constructs a URI from a single, parsable string.
     * <p>
     *     Uses: {@link java.net.URI#URI(String)}
     * </p>
     */
    Single(a -> new URI(a.getParsable()),
            a ->
                    a.getParsable() != null &&
                    a.getScheme() == null &&
                    a.getSchemeSpecificPart() == null &&
                    a.getAuthority() == null &&
                    a.getUserInfo() == null &&
                    a.getHost() == null &&
                    a.getPort() == -1 &&
                    a.getPath() == null &&
                    a.getQuery() == null &&
                    a.getFragment() == null
    ),

    /**
     * Constructs a URI with scheme, scheme-specific part, and fragment.
     * <p>
     *     Uses: {@link java.net.URI#URI(String, String, String)}
     * </p>
     * Uses: URI(String scheme, String schemeSpecificPart, String fragment)
     */
    SchemeAndSpecificPart(a -> new URI(a.getScheme(),a.getSchemeSpecificPart(),a.getFragment()),
            a ->
                    a.getScheme() != null &&
                    a.getSchemeSpecificPart() != null &&
                    a.getAuthority() == null &&
                    a.getUserInfo() == null &&
                    a.getHost() == null &&
                    a.getPort() == -1 &&
                    a.getPath() == null &&
                    a.getQuery() == null
    ),

    /**
     * Constructs a URI with full authority (userInfo, host, port), path, query, and fragment.
     * <p>
     *     Uses: {@link java.net.URI#URI(String, String, String, int, String, String, String)}
     * </p>
     */
    FullAuthority(a -> new URI(a.getScheme(),a.getUserInfo(),a.getHost(),a.getPort(),a.getPath(),a.getQuery(),a.getFragment()),
            a ->
                    a.getScheme() != null &&
                    (a.getUserInfo()!=null || a.getHost() != null || a.getPort()!=-1)
    ),

    /**
     * Constructs a URI with scheme, host, path, and fragment.
     * <p>
     *     Uses: {@link java.net.URI#URI(String, String, String, String)}
     * </p>
     */
    SimpleHostOrPath(a -> new URI(a.getScheme(),a.getHost(),a.getPath(),a.getFragment()),
            a ->
                    //a.getScheme() != null &&
                    a.getUserInfo() == null &&
                    (a.getHost() != null || a.getPath()!=null) &&
                    //a.getAuthority() == null &&
                    a.getPort() == -1 &&
                    a.getQuery() == null
    ),

    /**
     * Constructs a URI with scheme, authority, path, query, and fragment.
     * <p>
     *     Uses: {@link java.net.URI#URI(String, String, String, String,String)}
     * </p>
     */
    AuthorityBased(a -> new URI(a.getScheme(),a.getAuthority(),a.getPath(),a.getQuery(),a.getFragment()),
            a ->
                    a.getScheme() != null &&
                    a.getAuthority() != null &&
                    (a.getUserInfo()==null && a.getHost()==null && a.getPort()==-1)
    );

    @Getter
    private final URIConstructor constructor;

    @Getter
    private final Predicate<URIArgument> applicability;

    /**
     * Constructs a URI from the provided arguments.
     * @param arguments The URI arguments.
     * @return The constructed URI.
     * @throws URISyntaxException If the URI cannot be constructed.
     */
    public URI construct(URIArgument arguments) throws URISyntaxException {
        return constructor.construct(arguments);
    }

    /**
     * Tests if this constructor is applicable to the given arguments.
     * @param arguments The URI arguments to test.
     * @return True if the constructor can be used with the arguments, false otherwise.
     */
    public boolean isApplicable(URIArgument arguments) {
        return applicability.test(arguments);
    }

    public static Stream<StandardURIConstructor> streamValues() {
        return Arrays.stream(values());
    }

    public static Stream<StandardURIConstructor> streamByApplicability(URIArgument argument) {
        Objects.requireNonNull(argument);
        return streamValues().filter(e->e.isApplicable(argument));
    }

}
