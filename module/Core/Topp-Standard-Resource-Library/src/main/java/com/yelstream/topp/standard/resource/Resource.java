/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.resource;

import com.yelstream.topp.standard.resource.item.Item;
import com.yelstream.topp.standard.system.load.name.Location;

/**
 * Resource.
 * <p>
 *     Usually associated with a class-loader.
 * </p>
 * <p>
 *     This supports both in-memory (e.g., a "memory:" scheme) and file-based content
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
     * Gets the abstract location of this resource relative to the origin of the resource.
     * This is the resource name.
     *
     * @return Location.
     *         This must be non-{@code null}.
     */
    Location getLocation();

    /**
     * Gets the actual data content.
     * @return Data item.
     *         This must be non-{@code null}.
     */
    Item getItem();
}
