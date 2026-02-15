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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a module, package, or type as a demonstration / showcase application or component.
 * <p>
 *   Intended for runnable or near-complete examples that illustrate end-to-end usage,
 *   integration, feature combinations, or realistic application scenarios —
 *   not intended for production deployment without significant adaptation.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-02-14
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({
    ElementType.MODULE,
    ElementType.PACKAGE,
    ElementType.TYPE
})
public @interface Demonstration {
    /**
     * Short title or name of what this demonstration illustrates.
     */
    String value() default "";

    /**
     * Primary technologies, patterns, or features being showcased.
     */
    String showcases() default "";

    /**
     * Key simplifications, omissions, non-production aspects, or known limitations.
     */
    String limitations() default "";

    /**
     * When this demonstration was introduced or last verified.
     */
    String since() default "";

    /**
     * Reference to related guide, article, video, upstream documentation, or blog post.
     */
    String seeAlso() default "";
}
