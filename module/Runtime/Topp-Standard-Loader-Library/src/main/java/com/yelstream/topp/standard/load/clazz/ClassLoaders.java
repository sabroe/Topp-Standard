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

package com.yelstream.topp.standard.load.clazz;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.security.CodeSource;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@Slf4j
@UtilityClass
public class ClassLoaders {
    //TODO: Apply some trace logging, SLF4J style!

    public static ClassLoader getPlatformClassLoader() {
        return ClassLoader.getPlatformClassLoader();
    }

    public static ClassLoader getSystemClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }

    public static ClassLoader getModuleClassLoader(Module module) {
        return module.getClassLoader();
    }

    public static ClassLoader getModuleClassLoader(Class<?> clazz) {
        return getModuleClassLoader(clazz.getModule());
    }

    public static ClassLoader getContextClassLoader(Thread thread) {
        return thread.getContextClassLoader();
    }

    public static ClassLoader getContextClassLoader() {
        return getContextClassLoader(Thread.currentThread());
    }

    public static ClassLoader getCodeSourceClassLoader(CodeSource codeSource) {
        return codeSource.getClass().getClassLoader();
    }

    public static ClassLoader getCodeSourceClassLoader(Class<?> clazz) {
        return null;  //TODO:!
    }

    public static ClassLoader getClassLoader(Class<?> clazz) {
        return null;  //TODO:!
    }

    public static ClassLoader getCodeBaseClassLoader(Class<?> clazz) {
        return null;  //TODO: Has module? Or else has code-source?
    }
}
