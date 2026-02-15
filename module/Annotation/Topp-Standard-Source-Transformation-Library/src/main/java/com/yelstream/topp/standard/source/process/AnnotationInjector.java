package com.yelstream.topp.standard.source.process;

import java.lang.annotation.Annotation;

import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;

public final class AnnotationInjector {

    private AnnotationInjector() {}

    public static <A extends Annotation>
    CtAnnotation<A> inject(CtElement target, Class<A> annotationClass) {

        Factory factory = target.getFactory();

        // Prevent duplicates FIRST
        Annotation existing = target.getAnnotation(annotationClass);
        if (existing != null) {
            return null;
        }

        // Create typed annotation reference (NO CASTS NEEDED)
        CtTypeReference<A> ref =
                factory.Type().createReference(annotationClass);

        CtAnnotation<A> annotation =
                factory.createAnnotation(ref);

        target.addAnnotation(annotation);

        return annotation;
    }
}
