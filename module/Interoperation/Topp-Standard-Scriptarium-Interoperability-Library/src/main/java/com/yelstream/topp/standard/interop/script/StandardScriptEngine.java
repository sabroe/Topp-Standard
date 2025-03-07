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

package com.yelstream.topp.standard.interop.script;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Standard script context engine.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-07
 */
@AllArgsConstructor
@SuppressWarnings({"java:S115","LombokGetterMayBeUsed"})
public enum StandardScriptEngine {
    /**
     * Oracle Nashorn.
     */
    OracleNashorn("nashorn"),

    /**
     * Mozilla Rhino.
     */
    MozillaRhino("rhino"),

    /**
     * Oracle Graal JS.
     */
    OracleGraalJS("graal.js");

    @Getter
    private final String engineName;

    public ScriptEngine getEngine() {
        return ScriptEngines.getEngineByName(engineName);
    }

    public ScriptEngine getEngine(ScriptEngineManager manager) {
        return ScriptEngines.getEngineByName(manager,engineName);
    }
}
