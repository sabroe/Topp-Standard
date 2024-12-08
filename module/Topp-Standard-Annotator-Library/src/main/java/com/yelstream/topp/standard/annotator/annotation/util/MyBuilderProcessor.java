/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.annotator.annotation.util;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
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

@SupportedAnnotationTypes("MyBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyBuilderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(MyBuilder.class)) {
            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                generateAnnotatedClass(typeElement);
            }
        }
        return true;
    }

    private void generateAnnotatedClass(TypeElement typeElement) {
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        String className = typeElement.getSimpleName() + "Generated";
        String originalClassName = typeElement.getSimpleName().toString();

        String classContent = "package " + packageName + ";\n\n"
                + "import lombok.Builder;\n"
                + "import lombok.AllArgsConstructor;\n"
                + "import lombok.extern.slf4j.Slf4j;\n\n"
                + "@Slf4j\n"
                + "@Builder(builderClassName = \"Builder\")\n"
                + "@AllArgsConstructor(staticName = \"of\", access = lombok.AccessLevel.PRIVATE)\n"
                + "public class " + className + " extends " + originalClassName + " {\n"
                + "    // Generated class\n"
                + "}";

        try {
            JavaFileObject file = processingEnv.getFiler().createSourceFile(packageName + "." + className);
            try (Writer writer = file.openWriter()) {
                writer.write(classContent);
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to write generated class: " + e.getMessage());
        }
    }
}
