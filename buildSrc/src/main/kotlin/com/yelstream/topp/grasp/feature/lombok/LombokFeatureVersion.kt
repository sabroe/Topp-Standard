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

package com.yelstream.topp.grasp.feature.lombok

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyConstraintHandler
import org.gradle.api.attributes.Attribute

/**
 * Lombok feature version settings.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-11-13
 */
class LombokFeatureVersion private constructor() {
    companion object {
        const val LOMBOK_VERSION = "1.18.42"

        fun applyConstraints(project: Project,
                             handler: DependencyConstraintHandler) {
            val reason = "Default version set by LombokFeaturePlugin"
            val attr = Attribute.of("com.example.plugin", String::class.java)

            listOf(
                "compileOnly" to "org.projectlombok:lombok:${LOMBOK_VERSION}",
                "annotationProcessor" to "org.projectlombok:lombok:${LOMBOK_VERSION}",
                "testCompileOnly" to "org.projectlombok:lombok:${LOMBOK_VERSION}",
                "testAnnotationProcessor" to "org.projectlombok:lombok:${LOMBOK_VERSION}"
            ).forEach { (configurationName, dependencyNotation) ->
                handler.add(configurationName, dependencyNotation) {
                    because(reason)
                    attributes { attribute(attr, "LombokFeaturePlugin") }
                }
            }
        }
    }
}
