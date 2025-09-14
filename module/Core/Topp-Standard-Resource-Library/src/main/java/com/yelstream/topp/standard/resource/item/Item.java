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

package com.yelstream.topp.standard.resource.item;

import com.yelstream.topp.standard.io.dual.source.Source;
import com.yelstream.topp.standard.io.dual.target.Target;

import java.net.URI;
import java.net.URL;

/**
 * Resource data item.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-01
 */
public interface Item {
    /**
     * Gets an identification of this resource.
     * <p>
     * Note: This may not resolve to a URL with an actively supported scheme.
     * </p>
     *
     * @return Identification.
     * This may be {@code null}.
     * This may use non-JVM-wide, private schemes (e.g., "memory:").
     */
    URI getURI();

    /**
     * Gets a reference to the content of this resource.
     * <p>
     * Note: This may involve copying content to a temporary file for accessibility.
     * </p>
     *
     * @return Content reference.
     * This may be {@code null}.
     * May be distinct from {@code getURI().toURL()}.
     */
    URL getURL();

    /**
     *
     */
    Capability capability();

    /**
     * Creates an accessor for reading data.
     * @return Accessor for reading data.
     */
    Source readable();

    /**
     * Creates an accessor for writing data.
     * @return Accessor for writing data.
     */
    Target writable();
}
