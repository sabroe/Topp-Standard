package com.yelstream.topp.standard.interop.script;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

public class ListScriptEngines {
    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();

        System.out.println("Available Script Engines:");
        for (ScriptEngineFactory factory : manager.getEngineFactories()) {
            System.out.println("- " + factory.getEngineName() + " (" + factory.getLanguageName() + ")");
            System.out.println("  Extensions: " + factory.getExtensions());
            System.out.println("  Mime Types: " + factory.getMimeTypes());
            System.out.println("  Names: " + factory.getNames());
            System.out.println();
        }
    }
}
