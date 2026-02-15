package com.yelstream.topp.standard.source.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtType;

public class MyBuilderSpoonProcessor
        extends AbstractAnnotationProcessor<MyBuilder, CtType<?>> {

    @Override
    public void process(MyBuilder annotation, CtType<?> type) {

        AnnotationInjector.inject(type, Slf4j.class);

        CtAnnotation<Builder> builder =
                AnnotationInjector.inject(type, Builder.class);

        builder.addValue("builderClassName",
                getFactory().createLiteral("Builder"));

        CtAnnotation<AllArgsConstructor> allArgs =
                AnnotationInjector.inject(type, AllArgsConstructor.class);

        allArgs.addValue("staticName",
                getFactory().createLiteral("of"));

        allArgs.addValue("access",
                getFactory().Code()
                        .createCodeSnippetExpression("lombok.AccessLevel.PRIVATE"));
    }
}
