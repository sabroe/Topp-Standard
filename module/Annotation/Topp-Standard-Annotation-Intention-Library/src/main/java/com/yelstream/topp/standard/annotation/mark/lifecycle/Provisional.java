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
 * Indicates that this package, type, method or module is released (published in a repository),
 * but still considered provisional / evolving.
 * <p>
 *   Breaking changes, API adjustments or behavioral modifications are still possible.
 *   Use with caution in production systems.
 * </p>
 * <p>
 *   Typical use: early adopter feedback phase, incomplete test coverage in some areas,
 *   or API not yet fully locked.
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
public @interface Provisional {

    /**
     * Version or date when this provisional status was assigned.
     */
    String since() default "";

    /**
     * Short note about current limitations, known gaps or expected changes.
     */
    String note() default "";

    /**
     * Expected version/date when this component should reach stabilized or published status.
     */
    String expectedStabilization() default "";

    /**
     * Reference to tracking issue, ADR, migration guide or discussion.
     */
    String seeAlso() default "";
}
