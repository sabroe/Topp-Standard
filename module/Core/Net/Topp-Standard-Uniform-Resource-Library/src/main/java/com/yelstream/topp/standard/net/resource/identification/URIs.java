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

import com.yelstream.topp.standard.net.resource.identification.build.URIArgument;
import com.yelstream.topp.standard.net.resource.identification.build.URIConstructor;
import com.yelstream.topp.standard.net.resource.identification.build.URIConstructors;
import com.yelstream.topp.standard.net.resource.identification.handler.URISchemeHandler;
import lombok.experimental.UtilityClass;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLStreamHandler;

/**
 * Utility addressing instances of {@link URI}.
 * <p>
 *    This is the main entry point for creating {@link URI} instances.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
@UtilityClass
public class URIs {
    /**
     * Creates a URL from a URI.
     * @param uri URI.
     * @return Created URL.
     * @throws IllegalStateException Thrown in case of illegal argument.
     */
    public static URL toURL(URI uri) {
        try {
            return uri.toURL();
        } catch (MalformedURLException ex) {
            throw new IllegalStateException("Failure to convert URI to URL; actual URI is '%s'!".formatted(uri));
        }
    }

    /**
     * Creates a URL from a URI.
     * @param uri URI.
     * @param handler URL stream handler.
     * @return Created URL.
     * @throws IllegalStateException Thrown in case of illegal argument.
     */
    public static URL toURL(URI uri,
                            URLStreamHandler handler) {
        try {
            return URL.of(uri,handler);
        } catch (MalformedURLException ex) {
            throw new IllegalStateException("Failure to convert URI to URL; actual URI is '%s'!".formatted(uri));
        }
    }

    /**
     * Creates a URI.
     * <p>
     * The creation is on the premises of the existing constructors of {@link URI}.
     * </p>
     * <p>
     * For another example of a builder, with a different purpose of supporting REST, consider taking a look at the
     * <a href="https://jakarta.ee/specifications/restful-ws/4.0/apidocs/jakarta.ws.rs/jakarta/ws/rs/core/uribuilder">Jakarta JAX-RS UriBuilder</a>.
     * </p>
     * @param argument Argument.
     * @param constructor Constructor.
     * @param handler Handler.
     * @return Created URI.
     * @throws URISyntaxException Thrown in case of URI syntax error.
     */
    @lombok.Builder(builderClassName="Builder")  //TO-DO: Rename to 'URIBuilder'!
    @SuppressWarnings({"java:S2589", "java:S1066", "ConstantConditions", "unused", "java:S3776", "java:S107"})
    private static URI create(URIArgument argument,
                              URIConstructor constructor,
                              URISchemeHandler handler) throws URISyntaxException {

        constructor=URIConstructors.selectByApplicability(argument);
if (constructor==null) {
    System.out.println("--- NO CONSTRUCTOR!");
    System.out.println("   Argument:"+argument);
    System.out.println("---");
    System.out.println();
}

        URI uri = constructor.create(argument);
        return uri;
    }

    /**
     * Builder of {@link URI} instances.
     */
    @SuppressWarnings({"unused", "java:S1450", "FieldCanBeLocal", "UnusedReturnValue", "java:S1066"})
    public static class Builder {
        public Builder uri(URI uri) {
            argument(URIArgument.of(uri));
            constructor(null);
            handler(null);
            postCreate();
            return this;
        }

        /**
         * Applies state specific fiddling as a post action when all fields have been set.
         */
        @SuppressWarnings({"java:S1301", "SwitchStatementWithTooFewBranches"})
        private void postCreate() {
            URIArgument a=argument;
            if (a.getScheme()!=null) {
                switch (a.getScheme()) {
                    case "file" -> {
                        if (a.getSchemeSpecificPart().startsWith("//")) {
                            if (a.getHost() == null) {
                                a=a.toBuilder().host("").build();
                                argument(a);
                            }
                        }
                    }
                    default -> {
                        //Ignore!
                    }
                }
            }
        }

        public static Builder fromURI(URI uri) {
            return builder().uri(uri);
        }

        public static Builder fromURI(String uri) throws URISyntaxException {
            return fromURI(new URI(uri));
        }

        public static Builder fromURL(URL url) throws URISyntaxException {
            return fromURI(url.toURI());
        }

        public static Builder fromPath(String path) {
            return builder().argument(URIArgument.builder().path(path).build());
        }
    }
}
