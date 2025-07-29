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
import com.yelstream.topp.standard.net.resource.identification.build.URIConstructors;
import com.yelstream.topp.standard.net.resource.identification.scheme.Scheme;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-21
 */
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor(staticName="of")
final class SimpleURISchemeHandler implements URISchemeHandler {
    /**
     *
     */
    @Getter
    private final Scheme scheme;  //TO-DO: Consider this; may not be sane to tie this handler to one specific, named scheme!

    /**
     *
     */
    @Getter
    private final Trait trait;

    private final Function<URIArgument,String> entryReader;
    private final Function<URIArgument,Properties> propertiesReader;
    private final Function<URIArgument,String> tagReader;
    private final Function<URIArgument,String> innerURIReader;

    /**
     *
     */
    @Getter
    private final UnaryOperator<URIArgument> argumentCorrection;

    /**
     *
     */
    @Getter
    private final Function<URIArgument,URIConstructor> argumentConstructor;

    @Override
    public String getEntry(URIArgument argument) {
        return entryReader.apply(argument);
    }

    @Override
    public Properties getProperties(URIArgument argument) {
        return propertiesReader.apply(argument);
    }

    @Override
    public String getTag(URIArgument argument) {
        return tagReader.apply(argument);
    }

    @Override
    public String getInnerURI(URIArgument argument) {
        return innerURIReader.apply(argument);
    }

    @Override
    public URIArgument getCorrectedArgument(URIArgument argument) {
        return argumentCorrection.apply(argument);
    }

    @Override
    public URIConstructor getConstructor(URIArgument argument) {
        return argumentConstructor.apply(argument);
    }

    @SuppressWarnings({"unused","FieldMayBeFinal"})
    public static class Builder {
        private UnaryOperator<URIArgument> argumentCorrection=UnaryOperator.identity();
        private Function<URIArgument,URIConstructor> argumentConstructor=URIConstructors::selectByApplicability;
    }
}
