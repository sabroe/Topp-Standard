package com.yelstream.topp.standard.interop.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class RhinoTest {
    public static void main(String[] args) {
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();
            Object result = cx.evaluateString(scope, "2 + 3", "test", 1, null);
            System.out.println("Rhino result: " + result); // Should print: 5
        } finally {
            Context.exit();
        }
    }
}
