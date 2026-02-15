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

package com.yelstream.topp.standard.annotation.mark.lifecycle;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that this package, type, method or module has reached a stabilized state.
 * <p>
 *   The API and behavior are mostly frozen — only non-breaking changes, bug fixes,
 *   performance improvements or documentation updates are expected.
 * </p>
 * <p>
 *   Suitable for broader adoption, but not yet considered fully committed / production-grade forever.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-02-14
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({
        ElementType.PACKAGE,
        ElementType.TYPE,
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.CONSTRUCTOR,
        ElementType.MODULE
})
public @interface Stabilized {

    /**
     * Version or date when this component reached stabilized status.
     */
    String since() default "";

    /**
     * Optional note about what has been stabilized (API, behavior, configuration, etc.).
     */
    String note() default "";

    /**
     * Expected next lifecycle stage (usually "published") and rough timeframe.
     */
    String nextExpected() default "";

    /**
     * Reference to changelog, stability policy, or related documentation.
     */
    String seeAlso() default "";
}
