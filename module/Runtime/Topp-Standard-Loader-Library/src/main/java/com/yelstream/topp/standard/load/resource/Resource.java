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
 * <p>
 *     This supports both in-memory (e.g., a "memory:" scheme) and file-based catalogs
 *     while avoiding imposing any JVM-wide registrations for private schemes.
 *     <br/>
 *     However, a global, JVM-wide registration may be used.
 * </p>
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
     *     Note: This may not resolve to a URL with an actively supported scheme.
     * </p>
     * @return Identification.
     *         This may be {@code null}.
     *         This may use non-JVM-wide, private schemes (e.g., "memory:").
     */
    URI getURI();

    /**
     * Gets a reference to the content of this resource.
     * <p>
     *     Note: This may involve copying content to a temporary file for accessibility.
     * </p>
     * @return Content reference.
     *         This may be {@code null}.
     *         May be distinct from {@code getURI().toURL()}.
     */
    URL getURL();

    /**
     * Creates an accessor for reading the content of the resource.
     * @return Accessor for reading the content of the resource.
     */
    InputSource readable();
}
