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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class RhinoEngineTest {
    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("rhino");

        if (engine == null) {
            System.out.println("Rhino is NOT available.");
        } else {
            try {
                System.out.println(engine.eval("2 + 3")); // Should print: 5
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
