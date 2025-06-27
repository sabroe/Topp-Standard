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

import com.yelstream.topp.standard.load.resource.adapt.ResourceLoader;
import lombok.experimental.UtilityClass;

/**
 * Utility addressing instances of {@link Resource}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@UtilityClass
public class Resources {
    /**
     * Creates a resource.
     * @param classLoader Class-loader.
     * @param name Resource name.
     * @return Created resource.
     */
    public static Resource createResource(ClassLoader classLoader,
                                          String name) {
        return ClassLoaderResource.of(classLoader,name);
    }

    /**
     * Creates a resource.
     * @param resourceLoader Resource-loader.
     * @param name Resource name.
     * @return Created resource.
     */
    public static Resource createResource(ResourceLoader resourceLoader,
                                          String name) {
        return ResourceLoaderResource.of(resourceLoader,name);
    }

    /**
     * Creates a resource using a builder.
     * <p>
     *     Note that if manipulation with values it to take place,
     *     then create a manipulating resource-loader.
     * </p>
     * @param classLoader Class-loader.
     * @param resourceLoader Resource-loader.
     * @param name Resource name.
     * @return Created resource.
     */
    @lombok.Builder(builderClassName="Builder")
    private Resource createResourceByBuilder(ClassLoader classLoader,
                                             ResourceLoader resourceLoader,
                                             String name) {
        if (classLoader!=null && resourceLoader!=null) {
            throw new IllegalStateException("Failure to create resource; only one of class-loader and resource-loader may be set!");
        }
        if (classLoader==null && resourceLoader==null) {
            throw new IllegalStateException("Failure to create resource; one of class-loader and resource-loader must be set!");
        }
        if (classLoader!=null) {
            return createResource(classLoader,name);
        } else {
            return createResource(resourceLoader,name);
        }
    }
}
