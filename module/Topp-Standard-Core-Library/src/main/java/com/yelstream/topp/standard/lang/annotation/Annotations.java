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

package com.yelstream.topp.standard.lang.annotation;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

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
     * @param annotationClass Annotation.
     * @return Indicates, if object has annotation.
     */
    public static boolean hasAnnotation(Object object,
                                        Class<? extends Annotation> annotationClass) {
        return hasAnnotation(object.getClass(),annotationClass);
    }

    /**
     * Indicates, if a class has a specific annotation.
     * @param clazz Class.
     * @param annotationClass Annotation.
     * @return Indicates, if class has annotation.
     */
    public static boolean hasAnnotation(Class<?> clazz,
                                        Class<? extends Annotation> annotationClass) {
        return clazz.isAnnotationPresent(annotationClass);
    }
}
