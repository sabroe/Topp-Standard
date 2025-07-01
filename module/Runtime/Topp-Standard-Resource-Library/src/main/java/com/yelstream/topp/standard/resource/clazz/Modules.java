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

package com.yelstream.topp.standard.resource.clazz;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility addressing {@link Module} instances.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@Slf4j
@UtilityClass
public class Modules {
    /**
     * Gets the unnamed module for a classloader.
     * @param classLoader Classloader.
     * @return Unnamed module.
     */
    public static Module getUnnamedModule(ClassLoader classLoader) {
        return classLoader.getUnnamedModule();
    }

    /**
     * Gets the unnamed module for a class.
     * @param clazz Class.
     * @return Unnamed module.
     */
    public static Module getUnnamedModule(Class<?> clazz) {
        return getUnnamedModule(clazz.getClassLoader());
    }

    /**
     * Tests, if a class is within an unnamed module.
     * @param clazz Class.
     * @return Indicates, if a class is within an unnamed module.
     */
    public static boolean isInNamedModule(Class<?> clazz) {
        ClassLoader classLoader=clazz.getClassLoader();
        Module unnamedModule=getUnnamedModule(classLoader);
        Module module=clazz.getModule();
        return module==unnamedModule;
    }
}
