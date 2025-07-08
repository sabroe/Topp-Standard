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

package com.yelstream.topp.standard.resource;

import com.yelstream.topp.standard.resource.clazz.ClassLoaders;
import com.yelstream.topp.standard.resource.clazz.load.ResourceLoader;
import com.yelstream.topp.standard.resource.item.Item;
import com.yelstream.topp.standard.resource.item.Items;
import com.yelstream.topp.standard.resource.name.Location;
import com.yelstream.topp.standard.resource.name.Locations;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Function;

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
     * @param location Location.
     *                 Must be set.
     * @param item Item.
     *             Must be set.
     * @return Created resource.
     */
    public static Resource createResource(Location location,
                                          Item item) {
        Objects.requireNonNull(location);
        Objects.requireNonNull(item);
        return SimpleResource.of(location,item);
    }

    public static Resource createResource(Location location,
                                          Function<Location,Item> itemSupplier) {
        Objects.requireNonNull(location);
        Objects.requireNonNull(itemSupplier);
        return SuppliedItemResource.of(location,itemSupplier);
    }

    /**
     * Creates a resource.
     * @param classLoader Class-loader.
     * @param name Resource name.
     * @return Created resource.
     */
    public static Resource createResource(ClassLoader classLoader,
                                          String name,
                                          boolean container) {
        Location location=Locations.createLocation(name,container);
        Item item=Items.createItem(location,classLoader);
        return createResource(location,item);
    }

    /**
     * Creates a resource.
     * @param resourceLoader Resource-loader.
     * @param name Resource name.
     * @return Created resource.
     */
    public static Resource createResource(ResourceLoader resourceLoader,
                                          String name,
                                          boolean container) {
        Location location=Locations.createLocation(name,container);
        Item item=Items.createItem(location,resourceLoader);
        return createResource(location,item);
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
                                             String name,
                                             boolean container) {
        if (classLoader!=null && resourceLoader!=null) {
            throw new IllegalStateException("Failure to create resource; only one of class-loader and resource-loader may be set!");
        }
        if (classLoader==null && resourceLoader==null) {
            throw new IllegalStateException("Failure to create resource; one of class-loader and resource-loader must be set!");
        }
        if (classLoader!=null) {
            return createResource(classLoader,name,container);
        } else {
            return createResource(resourceLoader,name,container);
        }
    }

    /**
     *
     * @param classLoader
     * @param name
     * @return
     */
    public Resource getResource(ClassLoader classLoader,
                                String name) {
        Location location=Locations.createLocation(classLoader,name);
        if (location==null) {
            return null;
        } else {
            Item item=Items.createItem(location,classLoader);
            return createResource(location,item);
        }
    }

    public Resource getResource(Class<?> clazz,
                                String name) {
        ClassLoader classLoader=ClassLoaders.getClassLoader(clazz);
        return getResource(classLoader,name);
    }

    public Resource getResource(String name) {
        ClassLoader classLoader=ClassLoaders.getContextClassLoader();
        return getResource(classLoader,name);
    }
}
