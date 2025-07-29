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
import com.yelstream.topp.standard.net.resource.identification.scheme.Scheme;

import java.net.URI;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-15
 */
public interface URISchemeHandler {

    default Scheme getScheme() {  //TO-DO: Consider this; may not be sane to tie this handler to one specific, named scheme!
        return null;
    }

    Trait getTrait();

    String getEntry(URIArgument argument);
    Properties getProperties(URIArgument argument);
    String getTag(URIArgument argument);
    String getInnerURI(URIArgument argument);


    URIArgument getCorrectedArgument(URIArgument argument);

    URIConstructor getConstructor(URIArgument argument);

    static URI createURI(URIArgument argument,
                          UnaryOperator<URIArgument> argumentCorrection,
                          Function<URIArgument,URIConstructor> argumentConstructor) {
        argument=argumentCorrection.apply(argument);
        return argumentConstructor.apply(argument).create(argument);
    }

    default URI createURI(URIArgument argument) {
        return createURI(argument,this::getCorrectedArgument,this::getConstructor);
    }
}
