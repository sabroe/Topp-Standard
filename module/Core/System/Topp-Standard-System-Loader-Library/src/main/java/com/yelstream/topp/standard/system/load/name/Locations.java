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

package com.yelstream.topp.standard.system.load.name;

import lombok.experimental.UtilityClass;

/**
 * Utility addressing instances of {@code Location}
 * and classloader resource names in general.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-01
 */
@UtilityClass
public class Locations {
    /**
     * Root container name.
     * <p>
     *     Note that this matches {@link ClassLoader} semantics.
     *     <br/>
     *     This differs from any UNIX inspired style, using "/".
     * </p>
     */
    public static final String ROOT_CONTAINER_NAME="";

    /**
     * Separator of name elements.
     */
    public static final String SEPARATOR="/";

    public boolean isNameForContainer(String name) {
        if (name==null) {
            return false;
        }
        return name.isEmpty() || name.equals(SEPARATOR) || name.endsWith(SEPARATOR);
    }

    public boolean isNameForContent(String name) {
        if (name==null || name.isEmpty() || name.equals(SEPARATOR)) {
            return false;
        }
        return !name.endsWith(SEPARATOR);
    }

    public boolean isNameForContainerRoot(String name) {
        return name != null && (name.isEmpty() || name.equals(SEPARATOR));
    }

    public static String normalizeName(String name,
                                       boolean container) {
        if (name==null) {
            return container?ROOT_CONTAINER_NAME:null;
        }
        if (name.isEmpty() || name.equals(SEPARATOR)) {
            return container?ROOT_CONTAINER_NAME:null;
        }
        String normalized=name.replaceAll("//+","/").replaceFirst("^/","");
        if (normalized.isEmpty()) {
            return container?ROOT_CONTAINER_NAME:null;
        }
        if (container) {
            return normalized.endsWith(SEPARATOR)?normalized:normalized+SEPARATOR;
        }
        return normalized.endsWith(SEPARATOR)?normalized.substring(0,normalized.length()-1):normalized;
    }

    public static String normalizeNameAsContainer(String name) {
        return normalizeName(name,true);
    }

    public static String normalizeNameAsContent(String name) {
        return normalizeName(name,false);
    }

    public static String toString(Location location) {
        return location.getName();
    }

    /**
     * Create a simple location.
     * @param name Location name.
     * @param container Indicates, if location name refers to a container.
     * @return Created location.
     *         The name of this is always normalized.
     */
    public static Location createLocation(String name,
                                          boolean container) {
        String normalizedName=normalizeName(name,container);
        return SimpleLocation.of(normalizedName);
    }


    public static Location createLocation(ClassLoader classLoader,
                                          String name) {
        return null;  //TO-DO: !
    }

}
