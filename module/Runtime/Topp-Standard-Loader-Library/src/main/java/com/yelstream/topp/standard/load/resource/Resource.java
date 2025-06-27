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
import java.net.URL;

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
    /**
     * Gets the name of this resource.
     * @return Name.
     *         This may be {@code null}.
     */
    String getName();

    /**
     * Gets an identification of this resource.
     * <p>
     *     Note that the scheme may be symbolic and has no associated URL handler.
     *     To obtain a URL use {@link #getURL()}.
     * </p>
     * @return Identification.
     *         This may be {@code null}.
     */
    URI getURI();  //TO-DO: Grab hold of description set upon CatalogProvider! Fix detected package cycle!

    /**
     * Gets a reference to the content of this resource.
     * @return Content reference.
     *         This may be {@code null}.
     */
    URL getURL();  //TO-DO: Grab hold of description set upon CatalogProvider! Fix detected package cycle!

//    Stream<Resource> resources();

    InputSource read();  //TO-DO: -> "readable"? "createReadable()"?
}
