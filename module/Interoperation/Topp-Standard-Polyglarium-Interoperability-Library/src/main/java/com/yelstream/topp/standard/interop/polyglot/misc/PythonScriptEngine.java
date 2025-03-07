package com.yelstream.topp.standard.interop.polyglot.misc;

import org.graalvm.polyglot.Context;

public class PythonScriptEngine {
    public static void main(String[] args) {
        try (Context context = Context.create()) {
            context.eval("python", "print('Hello from Python!')");
        }
    }
}
