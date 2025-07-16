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

import com.yelstream.topp.standard.net.resource.identification.scheme.Scheme;
import com.yelstream.topp.standard.net.resource.identification.util.URIArgument;
import com.yelstream.topp.standard.net.resource.identification.util.URIConstructor;

import java.net.URI;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-15
 */
public interface URISchemeHandler {

    Scheme getScheme();

    boolean isRegular();

    URIArgument postConstruct(URIArgument argument);

    URI create(URIArgument argument,
               URIConstructor constructor);

    boolean hasInnerURI();

    URI getInnerURI();
    URIArgument getInnerURIArgument();

}
