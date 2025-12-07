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

package com.yelstream.topp.standard.interop.script;

import lombok.experimental.UtilityClass;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.List;

/**
 * Utilities addressing script engine managers.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-07
 */
@UtilityClass
public class ScriptEngineManagers {
    /**
     * Loader used by the default constructor of 'ScriptEngineManager'.
     */
    public static final ClassLoader DEFAULT_CLASSLOADER = Thread.currentThread().getContextClassLoader();

    public static ExtendedScriptEngineManager.Builder builder() {
        return ExtendedScriptEngineManager.builder();
    }

    public static ScriptEngineManager createScriptEngineManager() {
        return builder().build();
    }

    public static ScriptEngineManager createScriptEngineManager(ClassLoader loader) {
        return builder().loader(loader).build();
    }

    public static ScriptEngineManager createScriptEngineManager(List<ScriptEngineFactory> factories) {
        return builder().exact(factories).build();
    }

    public static ScriptEngineManager createScriptEngineManager(ScriptEngineFactory factory) {
        return builder().exact(List.of(factory)).build();
    }

    public static List<String> getNames(ScriptEngineManager manager) {
        return ScriptEngineFactories.getNames(manager.getEngineFactories());
    }

    public static List<String> getEngineNames(ScriptEngineManager manager) {
        return ScriptEngineFactories.getEngineNames(manager.getEngineFactories());
    }

    public static List<String> getEngineVersions(ScriptEngineManager manager) {
        return ScriptEngineFactories.getEngineVersions(manager.getEngineFactories());
    }

    public static List<String> getMimeTypes(ScriptEngineManager manager) {
        return ScriptEngineFactories.getMimeTypes(manager.getEngineFactories());
    }

    public static List<String> getExtensions(ScriptEngineManager manager) {
        return ScriptEngineFactories.getExtensions(manager.getEngineFactories());
    }

    public static List<String> getLanguageNames(ScriptEngineManager manager) {
        return ScriptEngineFactories.getLanguageNames(manager.getEngineFactories());
    }

    public static List<String> getLanguageVersions(ScriptEngineManager manager) {
        return ScriptEngineFactories.getLanguageVersions(manager.getEngineFactories());
    }
}
