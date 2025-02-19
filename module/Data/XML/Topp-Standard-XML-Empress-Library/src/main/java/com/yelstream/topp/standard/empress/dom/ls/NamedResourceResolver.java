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

package com.yelstream.topp.standard.empress.dom.ls;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Resolver of input sources for data using named classpath resources.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@Slf4j
@AllArgsConstructor(staticName="of")
public class NamedResourceResolver implements LSResourceResolver {
    /**
     * Class-loader to look up resources.
     */
    private final ClassLoader classLoader;

    /**
     * Root resource name to use for base-URI, if not set.
     */
    private final String rootResource;

    @Override
    public LSInput resolveResource(String type,
                                   String namespaceURI,
                                   String publicId,
                                   String systemId,
                                   String baseURI) {
        LSInput input=null;

        log.atDebug().setMessage("Resolving resource; type {}, namespace-URI {}, public-id {}, system-id {}, base-URI {}.").addArgument(type).addArgument(namespaceURI).addArgument(publicId).addArgument(systemId).addArgument(baseURI).log();

        if (baseURI==null) {
            baseURI=rootResource;
        }

        if (baseURI!=null && systemId!=null) {
            try {
                URI baseUri = new URI(baseURI);

                URI resolvedUri = baseUri.resolve(systemId);
                String resourceName=resolvedUri.toString().replaceFirst("^resource:/", "");

                InputStream inputStream=classLoader.getResourceAsStream(resourceName);

                if (inputStream!=null) {
                    input= BasicLSInput.builder().byteStream(inputStream).publicId(publicId).systemId(systemId).baseURI(baseURI).build();
                }
            } catch (URISyntaxException ex) {
                throw new IllegalStateException(String.format("Failure to resolve resource; system-id is %s.",systemId),ex);
            }
        }

        return input;
    }

    public static NamedResourceResolver of(String rootResource) {
        return of(NamedResourceResolver.class.getClassLoader(),rootResource);
    }
}
