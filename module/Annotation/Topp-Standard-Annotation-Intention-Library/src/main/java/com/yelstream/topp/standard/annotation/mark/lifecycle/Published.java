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
 * Indicates that this package, type, method or module is fully published / committed.
 * <p>
 *   The component is considered production-grade, stable, and follows semantic versioning rules.
 *   Breaking changes are only introduced with major version bumps and appropriate deprecation periods.
 * </p>
 * <p>
 *   This is the recommended status for most public / shared production usage.
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
public @interface Published {

    /**
     * Version when this component was first published / declared stable.
     */
    String since() default "";

    /**
     * Optional maturity level or additional classification
     * (e.g. "GA", "Long Term Support", "Core API", etc.).
     */
    String maturity() default "";

    /**
     * Reference to stability policy, deprecation guide, or long-term support statement.
     */
    String seeAlso() default "";
}
