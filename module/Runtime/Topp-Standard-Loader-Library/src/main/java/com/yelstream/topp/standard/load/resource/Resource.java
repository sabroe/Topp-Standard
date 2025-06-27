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

package com.yelstream.topp.standard.load.resource;

import com.yelstream.topp.standard.load.io.InputSource;

import java.net.URI;

/**
 * Resource.
 * <p>
 *     Usually associated with a class-loader.
 * </p>
 *
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
public interface Resource {

    String getName();

    URI getURI();  //TO-DO: Grab hold of description set upon CatalogProvider! Fix detected package cycle!

//    URL getURL();  //TO-DO: Grab hold of description set upon CatalogProvider! Fix detected package cycle!

//    Stream<Resource> resources();

    InputSource read();  //TO-DO: -> "readable"? "createReadable()"?
}
