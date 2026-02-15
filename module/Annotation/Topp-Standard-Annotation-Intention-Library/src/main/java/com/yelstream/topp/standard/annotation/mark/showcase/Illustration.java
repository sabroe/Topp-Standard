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

package com.yelstream.topp.standard.annotation.mark.showcase;

import java.lang.annotation.*;

/**
 * Marks a type, method, or field as a technical illustration of a specific concept,
 * pattern, technique, mechanism, or API usage.
 * <p>
 *   Intended for focused, often non-runnable or partial code snippets that demonstrate
 *   an idea purely for educational, explanatory, or reference purposes.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-02-14
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({
    ElementType.TYPE,
    ElementType.METHOD,
    ElementType.FIELD
})
public @interface Illustration {
    /**
     * Short title describing what is being illustrated.
     */
    String value() default "";

    /**
     * The specific concept, mechanism, pattern, technique, or usage being demonstrated.
     */
    String illustrates() default "";

    /**
     * Surrounding context, prerequisites, related technologies, or typical use-case.
     */
    String context() default "";

    /**
     * When this illustration was introduced or last verified.
     */
    String since() default "";

    /**
     * Reference to guide, article, video, upstream documentation, or related material.
     */
    String seeAlso() default "";
}
