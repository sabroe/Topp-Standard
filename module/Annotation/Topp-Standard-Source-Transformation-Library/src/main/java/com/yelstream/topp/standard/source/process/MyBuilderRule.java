package com.yelstream.topp.standard.source.process;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtType;

import java.util.Objects;

public class MyBuilderRule {

    public static void apply(CtModel model) {

        model.getElements(type ->
                type instanceof CtType
                        && ((CtType<?>) type).getAnnotation(MyBuilder.class) != null
        ).forEach(element -> {

            CtType<?> type = (CtType<?>) element;

            AnnotationInjector.inject(type, Slf4j.class);

            Objects.requireNonNull(AnnotationInjector.inject(type, Builder.class))
                    .addValue("builderClassName",
                            type.getFactory().createLiteral("Builder"));

            Objects.requireNonNull(AnnotationInjector.inject(type, AllArgsConstructor.class))
                    .addValue("staticName",
                            type.getFactory().createLiteral("of"));
        });
    }

    /*
Launcher launcher = new Launcher();

launcher.addInputResource("src/main/java");

launcher.buildModel();

CtModel model = launcher.getModel();

MyBuilderRule.apply(model);

launcher.prettyprint();
     */
}
