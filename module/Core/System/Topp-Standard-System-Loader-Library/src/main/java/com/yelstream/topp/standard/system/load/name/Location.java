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

package com.yelstream.topp.standard.system.load.name;

/**
 * Location of a resource by name.
 * <p>
 *     The concepts applied:
 * </p>
 * <ol>
 *     <li>Location -- resource name</li>
 *     <li>Container -- container of resources, a directory.</li>
 *     <li>Content -- resource content, a file.</li>
 * </ol>
 * <p>
 *     Locations of type "container" and locations of type "content" are intended to be mutually exclusive.
 * </p>
 * <p>
 *     When classloader related,
 *     then {@link #getName()} and {@code #toString()} can be applied directly to {@link ClassLoader}.
 * </p>
 * <p>
 *     Note that implementations are expected to implement {@link Object#toString()} by returning
 *     the resource name as provided by {@link #getName()}.
 * </p>
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-01
 */
public interface Location {
    /**
     * Gets the name of a resource.
     * <p>
     *     If classloader based, this is applicable to {@link ClassLoader#getResource(String)}.
     * </p>
     * @return Resource name.
     */
    String getName();

    /**
     * Normalizes this location.
     * @return Normalized location.
     */
    Location normalize();

    /**
     * Gets an indication if this location refers to a content resource.
     * @return Indicates if this location refers to a content resource.
     */
    boolean isContent();

    /**
     * Gets an indication if this location refers to a container resource.
     * @return Indicates if this location refers to a container resource.
     */
    boolean isContainer();

    /**
     * Gets an indication if this location refers to the root container resource.
     * @return Indicates if this location refers to the root container resource.
     */
    boolean isContainerRoot();
}
