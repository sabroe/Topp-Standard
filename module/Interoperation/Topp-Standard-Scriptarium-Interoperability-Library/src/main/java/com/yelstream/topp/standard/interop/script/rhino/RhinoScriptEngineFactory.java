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

package com.yelstream.topp.standard.interop.script.rhino;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.util.List;
import java.util.StringJoiner;

/**
 * Java script-engine factory for the Mozilla Rhino JavaScript engine.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-07
 */
public class RhinoScriptEngineFactory implements ScriptEngineFactory {

    @Override
    public String getEngineName() {
        return "Rhino";
    }

    @Override
    public String getEngineVersion() {
        return "1.8.0";
    }

    @Override
    public List<String> getNames() {
        return List.of("rhino","javascript","js");
    }

    @Override
    public List<String> getMimeTypes() {
        return List.of("application/javascript","text/javascript");
    }

    @Override
    public List<String> getExtensions() {
        return List.of("js");
    }

    @Override
    public String getLanguageName() {
        return "JavaScript";
    }

    @Override
    public String getLanguageVersion() {
        return "1.8";
    }

    @Override
    public Object getParameter(String key) {
        return switch (key) {
            case ScriptEngine.NAME -> getEngineName();
            case ScriptEngine.ENGINE -> getEngineName();
            case ScriptEngine.ENGINE_VERSION -> getEngineVersion();
            case ScriptEngine.LANGUAGE -> getLanguageName();
            case ScriptEngine.LANGUAGE_VERSION -> getLanguageVersion();
            default -> null;
        };
    }

    @Override
    public String getMethodCallSyntax(String obj, String method, String... args) {
        StringJoiner joiner = new StringJoiner(", ");
        for (String arg : args) {
            joiner.add(arg);
        }
        return obj + "." + method + "(" + joiner + ");";
    }

    @Override
    public String getOutputStatement(String toDisplay) {
        return "print(\"" + toDisplay + "\");";
    }

    @Override
    public String getProgram(String... statements) {
        return String.join("\n", statements);
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new RhinoScriptEngine(this);
    }
}
