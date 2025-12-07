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

package com.yelstream.topp.standard.resource.item;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-01
 */
public interface Capability {
    /**
     * Gets an indication if this location refers to an existing resource.
     * @return Indicates if this location refers to an existing resource.
     */
    boolean isExisting();

    /**
     * Gets an indication if this location has readable content.
     * <p>
     *     Per default, expect content locations to have readable content, while container locations do not.
     * </p>
     * @return Indicates, if this location has readable content.
     */
    boolean isReadable();

    /**
     * Gets an indication if this location accepts writable content.
     * <p>
     *     Per default, expect that all locations are static and cannot be written to.
     * </p>
     * @return Indicates, if this location accepts writable content.
     */
    boolean isWritable();

    /**
     * Gets an indication if this location can be traversed to discover named resource, possibly new resources.
     * <p>
     *     Per default, expect that all container locations can be traversed.
     * </p>
     * <p>
     *     Additionally, if from a static resource, then there is no reason to repeat the initial discovery
     *     by extra traversal of container locations.
     * </p>
     * @return Indicates, if this location can be traverse to discover resources.
     */
    boolean isTraversable();

    default boolean exists() {
        return isExisting();
    }
}
