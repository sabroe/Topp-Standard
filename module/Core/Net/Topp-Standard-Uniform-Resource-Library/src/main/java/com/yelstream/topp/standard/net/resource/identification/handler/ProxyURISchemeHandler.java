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

package com.yelstream.topp.standard.net.resource.identification.handler;

import com.yelstream.topp.standard.net.resource.identification.build.URIArgument;
import com.yelstream.topp.standard.net.resource.identification.build.URIConstructor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * Proxy of a {@link URISchemeHandler} instance.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-28
 */
@AllArgsConstructor
public class ProxyURISchemeHandler implements URISchemeHandler {
    /**
     *
     */
    private final Supplier<URISchemeHandler> handlerSupplier;

    /**
     *
     */
    @Getter(lazy=true,value=AccessLevel.PRIVATE)
    private final URISchemeHandler handler=handlerSupplier.get();

    @Override
    public Trait getTrait() {
        return getHandler().getTrait();
    }

    @Override
    public String getEntry(URIArgument argument) {
        return getHandler().getEntry(argument);
    }

    @Override
    public Properties getProperties(URIArgument argument) {
        return getHandler().getProperties(argument);
    }

    @Override
    public String getTag(URIArgument argument) {
        return getHandler().getTag(argument);
    }

    @Override
    public String getInnerURI(URIArgument argument) {
        return getHandler().getInnerURI(argument);
    }

    @Override
    public URIArgument getCorrectedArgument(URIArgument argument) {
        return getHandler().getCorrectedArgument(argument);
    }

    @Override
    public URIConstructor getConstructor(URIArgument argument) {
        return getHandler().getConstructor(argument);
    }

    @Override
    public URI createURI(URIArgument argument) {
        return URISchemeHandler.createURI(argument,this::getCorrectedArgument,this::getConstructor);
    }
}
