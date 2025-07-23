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

import lombok.experimental.UtilityClass;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLStreamHandler;

/**
 * Utility addressing instances of {@link URL}.
 * <p>
 *    This is the main entry point for creating {@link URL} instances.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
@UtilityClass
public class URLs {
    /**
     * Creates a URI from a URL.
     * @param url URL.
     * @return Created URI.
     * @throws IllegalStateException Thrown in case of illegal argument.
     */
    public static URI toURI(URL url) {
        try {
            return url.toURI();
        } catch (URISyntaxException ex) {
            throw new IllegalStateException("Failure to convert URL to URI; actual URL is '%s'!".formatted(url));
        }
    }

    private static URL of(URI uri,
                          URLStreamHandler handler) throws MalformedURLException {
        return URL.of(uri,handler);
    }

    private static URL of(URL url,
                          URLStreamHandler handler) throws MalformedURLException {
        URI uri=toURI(url);
        return URL.of(uri,handler);
    }


    private static URL of(String uriText,
                          URLStreamHandler handler) throws MalformedURLException {
        URI uri=URI.create(uriText);
        return URL.of(uri,handler);
    }


}
