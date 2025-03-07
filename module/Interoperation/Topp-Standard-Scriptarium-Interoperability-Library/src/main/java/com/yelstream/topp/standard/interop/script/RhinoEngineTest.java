package com.yelstream.topp.standard.interop.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
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
