/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.smallrye.config.net;

import io.smallrye.config.WithName;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.function.Supplier;

interface URIConfig {  //TO-DO: Purpose; is this any good at all?
    @WithName("uri")
    Optional<URI> uri();

    @WithName("host")
    Optional<String> host();

    @WithName("port")
    Optional<Integer> port();

    default URL createURL(Supplier<URL> defaultURLSupplier) {
        URI uri=createURI(()->{
            URL url=defaultURLSupplier.get();
            try {
                return url.toURI();
            } catch (URISyntaxException ex) {
                throw new IllegalArgumentException(String.format("Failure to create URI; URL cannot be converted to an URI, URL is %s!",url),ex);
            }
        });
        try {
            return uri.toURL();
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(String.format("Failure to create URL; URI cannot be converted to an URL, URI is %s!",uri),ex);
        }
    }

    default URI createURI(Supplier<URI> defaultURISupplier) {
/*
        UriBuilder builder=UriBuilder.fromUri(uri().orElse(defaultURISupplier.get()));
        serverConfig.host().ifPresent(builder::host);
        serverConfig.port().ifPresent(builder::port);
*/
        return null;
    }
}
