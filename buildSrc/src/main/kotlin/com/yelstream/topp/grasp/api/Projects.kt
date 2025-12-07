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

package com.yelstream.topp.grasp.api

import org.gradle.api.Project
import java.util.Optional

/**
 * Utilities addressing {@link Project} instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-11-16
 */
class Projects private constructor() {
    companion object {
        @JvmStatic
        fun propertyValue(project: Project,
                          propertyName: String
                         ): Optional<String> {
            val propertyValueText: String? = project.findProperty(propertyName)?.toString()?.trim()
            return propertyValueText?.let { Optional.of(it) }
                ?: Optional.empty()
        }

        @JvmStatic
        @JvmOverloads
        fun enabled(project: Project,
                    propertyName: String,
                    defaultPropertyValue: Boolean = true): Boolean {
            val propertyValueText: String? = project.findProperty(propertyName)?.toString()?.trim()
            return propertyValueText?.toBooleanStrictOrNull() ?: defaultPropertyValue
        }

        @JvmStatic
        fun enabled(project: Project,
                    propertyName: String,
                   ): Optional<Boolean> {
            val propertyValueText: String? = project.findProperty(propertyName)?.toString()?.trim()
            return propertyValueText?.toBooleanStrictOrNull()?.let { Optional.of(it) }
                ?: Optional.empty()
        }
    }
}
