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

package com.yelstream.topp.standard.lang.annotation;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Utility addressing annotation handling.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-23
 */
@Slf4j
@UtilityClass
public class Annotations {
    /**
     * Indicates, if an object has a specific annotation.
     * @param object Object.
     * @param annotation Annotation.
     * @return Indicates, if object has annotation.
     */
    public static boolean hasAnnotation(Object object,
                                        Class<? extends Annotation> annotation) {
        return hasAnnotation(object.getClass(),annotation);
    }

    /**
     * Indicates, if a class has a specific annotation.
     * @param clazz Class.
     * @param annotation Annotation.
     * @return Indicates, if class has annotation.
     */
    public static boolean hasAnnotation(Class<?> clazz,
                                        Class<? extends Annotation> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    /**
     * Gets a list of simple, textual names for annotations present upon the class of an object.
     * @param object Object.
     * @param annotation Annotations.
     * @return List of annotation names.
     */
    public static List<String> toSimpleNames(Object object,
                                            List<Class<? extends Annotation>> annotation) {
        return toSimpleNames(object.getClass(),annotation);
    }

    /**
     * Gets a list of simple, textual names for annotations present upon a class.
     * @param clazz Class.
     * @param annotation Annotations.
     * @return List of annotation names.
     */
    public static List<String> toSimpleNames(Class<?> clazz,
                                             List<Class<? extends Annotation>> annotation) {
        return toSimpleNames(filterByPresence(clazz,annotation));
    }

    /**
     * Filters a list of annotations by presence upon a class.
     * @param clazz Class.
     * @param annotation Annotations.
     * @return List of annotations present upon class.
     */
    public static List<Class<? extends Annotation>> filterByPresence(Class<?> clazz,
                                                                     List<Class<? extends Annotation>> annotation) {
        return annotation.stream().filter(x->hasAnnotation(clazz,x)).toList();
    }

    /**
     * Gets a list of simple, textual names for annotations.
     * @param annotation Annotations.
     * @return List of annotation names.
     */
    public static List<String> toSimpleNames(List<Class<? extends Annotation>> annotation) {
        return annotation.stream().map(Class::getSimpleName).toList();
    }
}
