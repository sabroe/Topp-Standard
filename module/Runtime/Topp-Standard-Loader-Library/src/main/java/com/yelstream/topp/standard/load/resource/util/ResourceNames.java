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

package com.yelstream.topp.standard.load.resource.util;

import lombok.experimental.UtilityClass;

/**
 * Utility addressing instance of {@code #ResourceName}
 * and classloader resource names in general.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@UtilityClass
public class ResourceNames {

    public static final String root="";

    public static String normalizeName(String name,
                                       boolean file) {
        return null;
    }

    public static String normalizeDirectory(String name) {
        return null;
    }



    public static ResourceName createResourceName(String name) {
        return SimpleResourceName.of(name);
    }


    public String normalize(String name) {
        return null;  //TO-DO: !
    }

    public String normalizeDirectory(String name) {
        return null;  //TO-DO: !
    }

    public String normalizeFile(String name) {
        return null;  //TO-DO: !
    }

    public boolean isFile(String name) {
        return false;  //TO-DO: !
    }

    public boolean isDirectory(String name) {
        return false;  //TO-DO: !
    }

    public boolean isRoot(String name) {
        return false;  //TO-DO: !
    }
}
