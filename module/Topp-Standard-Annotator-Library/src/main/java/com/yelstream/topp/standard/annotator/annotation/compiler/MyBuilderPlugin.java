package com.yelstream.topp.standard.annotator.annotation.compiler;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.tools.javac.api.BasicJavacTask;

public class MyBuilderPlugin implements Plugin {  //javac -cp . -Xplugin:MyBuilderPlugin MyClass.java

    @Override
    public String getName() {
        return "MyBuilderPlugin";
    }

    @Override
    public void init(JavacTask task, String... args) {
        task.addTaskListener(new MyBuilderTaskListener((BasicJavacTask) task));
    }
}
