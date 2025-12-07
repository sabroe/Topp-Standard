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

package com.yelstream.topp.standard.annotator.annotation.compiler;

import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;

import com.yelstream.topp.standard.annotator.annotation.util.MyBuilder;

public class MyBuilderTaskListener implements TaskListener {
/*

With the plugin active, any class annotated with @MyBuilder will have the @Slf4j, @Builder,
and @AllArgsConstructor annotations added during the compile process, without generating any extra classes.

javac -cp . -Xplugin:MyBuilderPlugin MyClass.java

 */
    private final BasicJavacTask task;

    public MyBuilderTaskListener(BasicJavacTask task) {
        this.task = task;
    }

    @Override
    public void started(TaskEvent e) {
        // Not used in this example
    }

    @Override
    public void finished(TaskEvent e) {
        if (e.getKind() != TaskEvent.Kind.ANALYZE) {
            return;
        }

        JCTree.JCCompilationUnit compilationUnit = (JCTree.JCCompilationUnit) e.getCompilationUnit();
        compilationUnit.defs.forEach(def -> {
            if (def instanceof JCTree.JCClassDecl) {
                JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) def;
                processClass(classDecl);
            }
        });
    }

    private void processClass(JCTree.JCClassDecl classDecl) {
        TreeMaker treeMaker = TreeMaker.instance(task.getContext());
        Names names = Names.instance(task.getContext());

        // Check if class is annotated with @MyBuilder
        boolean hasMyBuilder = classDecl.mods.annotations.stream()
                .anyMatch(anno -> anno.type.toString().equals(MyBuilder.class.getCanonicalName()));

        if (hasMyBuilder) {
            // Add @Slf4j annotation
            classDecl.mods.annotations = classDecl.mods.annotations.prepend(
                    treeMaker.Annotation(
                            treeMaker.Ident(names.fromString("lombok.extern.slf4j.Slf4j")),
                            List.nil()
                    )
            );

            // Add @Builder annotation
            classDecl.mods.annotations = classDecl.mods.annotations.prepend(
                    treeMaker.Annotation(
                            treeMaker.Ident(names.fromString("lombok.Builder")),
                            List.nil()
                    )
            );

            // Add @AllArgsConstructor annotation
            classDecl.mods.annotations = classDecl.mods.annotations.prepend(
                    treeMaker.Annotation(
                            treeMaker.Ident(names.fromString("lombok.AllArgsConstructor")),
                            List.nil()
                    )
            );
        }
    }
}
