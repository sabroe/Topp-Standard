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

package com.yelstream.topp.standard.resource.clazz.load;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

/**
 * Loader of individually named resource.
 * <p>
 *     This is mimicking the method signatures of {@link ClassLoader}.
 * </p>
 * <p>
 *     This is essentially a low-level service-provider-interface, not to be used directly.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
public interface ResourceLoader {
    /**
     * Gets a reference to a resource.
     * <p>
     *     If multiple resources with the same name exists, then this is one of them.
     *     To get all resources with the same name,
     *     use {@link #getResources(String)}, {@link #resources(String)} or {@link #getResourcesAsList(String)}.
     * </p>
     * <p>
     *     See {@link ClassLoader#getResource(String)}.
     * </p>
     * @param name Resource name.
     * @return Reference to resource.
     *         This can be used to access the resource.
     */
    URL getResource(String name);

    /**
     * Gets access to resource content as an input-stream.
     * <p>
     *     If multiple resources with the same name exists, then this opens access to one of them.
     *     To get all resources with the same name,
     *     use {@link #getResources(String)}, {@link #resources(String)} or {@link #getResourcesAsList(String)},
     *     and use the reference URLs to open access.
     * </p>
     * <p>
     *     See {@link ClassLoader#getResourceAsStream(String)}.
     * </p>
     * @param name Resource name.
     * @return Input-stream to resource content.
     */
    InputStream getResourceAsStream(String name);

    /**
     * Gets references to all resources with the same name.
     * <p>
     *     See {@link ClassLoader#getResources(String)}.
     * </p>
     * <p>
     *     If multiple resources are present,
     *     then it may be one file URL for classes and one file URL for resources.
     * </p>
     * @param name Resource name.
     * @return References to resources.
     *         These can be used to access the resources.
     */
    Enumeration<URL> getResources(String name) throws IOException;

    /**
     * Gets references to all resources with the same name.
     * <p>
     *     See {@link ClassLoader#resources(String)}.
     * </p>
     * <p>
     *     If multiple resources are present,
     *     then it may be one file URL for classes and one file URL for resources.
     * </p>
     * @param name Resource name.
     * @return References to resources.
     *         These can be used to access the resources.
     */
    Stream<URL> resources(String name);

    /**
     * Gets access to resource content as a readable byte-channel.
     * <p>
     *     If multiple resources with the same name exists, then this opens access to one of them.
     *     To get all resources with the same name,
     *     use {@link #getResources(String)}, {@link #resources(String)} or {@link #getResourcesAsList(String)},
     *     and use the reference URLs to open access.
     * </p>
     * <p>
     *     See {@link ClassLoader#getResourceAsStream(String)}.
     * </p>
     * @param name Resource name.
     * @return Readable byte-channel to resource content.
     */
    default ReadableByteChannel getResourceAsChannel(String name) {
        InputStream stream=getResourceAsStream(name);
        return stream==null?null:Channels.newChannel(stream);
    }

    /**
     * Indicates, if there exists a resource.
     * @param name Resource name.
     * @return Indicates, if the resource exists.
     */
    default boolean hasResource(String name) {
        return getResource(name)!=null;
    }

    /**
     * Gets the number of resources with the same name.
     * @param name Resource name.
     * @return Number of resources.
     */
    default int getResourceCount(String name) {
        return (int)resources(name).count();
    }

    /**
     * Gets references to all resources with the same name.
     * @param name Resource name.
     * @return References to resources.
     *         These can be used to access the resources.
     */
    default List<URL> getResourcesAsList(String name) {
        return resources(name).toList();
    }
}
