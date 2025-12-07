/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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
import com.yelstream.topp.standard.net.resource.identification.handler.URISchemeHandler;
import com.yelstream.topp.standard.net.resource.identification.handler.factory.URISchemeHandlerFactories;
import lombok.experimental.UtilityClass;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.function.Function;

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
     * @param handlerSupplier Handler supplier.
     * @return Created URI.
     */
    @lombok.Builder(builderClassName="Builder")  //TO-DO: Rename to 'URIBuilder'!
    @SuppressWarnings({"java:S2589", "java:S1066", "ConstantConditions", "unused", "java:S3776", "java:S107"})
    private static URI create(URIArgument argument,
                              Function<URIArgument,URISchemeHandler> handlerSupplier) {
        return handlerSupplier.apply(argument).createURI(argument);
    }

    /**
     * Builder of {@link URI} instances.
     */
    @SuppressWarnings({"unused", "java:S1450", "FieldCanBeLocal", "UnusedReturnValue", "java:S1066","FieldMayBeFinal"})
    public static class Builder {
        private Function<URIArgument,URISchemeHandler> handlerSupplier=URISchemeHandlerFactories::getHandler;

        public Builder uri(URI uri) {
            argument(URIArgument.of(uri));
            return this;
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
