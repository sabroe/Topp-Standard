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

package com.yelstream.topp.standard.annotation.process.util;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * Modern annotation processor that generates a subclass with Lombok annotations
 * for classes annotated with @MyBuilder.
 */
@SupportedAnnotationTypes("com.yelstream.topp.standard.annotation.process.util.MyBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class MyBuilderProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(MyBuilder.class)) {
            if (element instanceof TypeElement typeElement) {
                generateSubclass(typeElement);
            }
        }
        return true;  // We claimed these annotations
    }

    private void generateSubclass(TypeElement typeElement) {
        String packageName = processingEnv.getElementUtils()
                .getPackageOf(typeElement)
                .getQualifiedName()
                .toString();

        String originalSimpleName = typeElement.getSimpleName().toString();
        String generatedSimpleName = originalSimpleName + "Generated";
        String qualifiedGeneratedName = packageName + "." + generatedSimpleName;

        StringBuilder content = new StringBuilder();
        content.append("package ").append(packageName).append(";\n\n")
                .append("import lombok.extern.slf4j.Slf4j;\n")
                .append("import lombok.Builder;\n")
                .append("import lombok.AllArgsConstructor;\n\n")
                .append("@Slf4j\n")
                .append("@Builder(builderClassName = \"Builder\")\n")
                .append("@AllArgsConstructor(staticName = \"of\", access = lombok.AccessLevel.PRIVATE)\n")
                .append("public class ").append(generatedSimpleName)
                .append(" extends ").append(originalSimpleName).append(" {\n")
                .append("    // Generated subclass with Lombok support\n")
                .append("}\n");

        try {
            JavaFileObject file = filer.createSourceFile(qualifiedGeneratedName, typeElement);
            try (Writer writer = file.openWriter()) {
                writer.write(content.toString());
            }
            messager.printMessage(Diagnostic.Kind.NOTE,
                    "Generated subclass: " + qualifiedGeneratedName);
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR,
                    "Failed to generate " + qualifiedGeneratedName + ": " + e.getMessage(),
                    typeElement);
        }
    }
}
