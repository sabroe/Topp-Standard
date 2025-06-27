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

package com.yelstream.topp.standard.load.resource.lookup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.stream.Stream;

/**
 * Adapter facilitating lookup of resource specifics.
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
public interface ResourceLookup {
    URL getResource(String name);

    InputStream getResourceAsStream(String name);

    Enumeration<URL> getResources(String name) throws IOException;

    Stream<URL> resources(String name);

    default boolean hasResource(String name) {
        return getResource(name)!=null;
    }
}
