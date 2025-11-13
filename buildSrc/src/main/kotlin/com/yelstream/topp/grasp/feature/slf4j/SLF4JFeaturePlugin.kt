/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.grasp.feature.slf4j

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.kotlin.dsl.*

/**
 * SLF4J feature plugin.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-11-13
 */
class SLF4JFeaturePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        //Note: 'JavaLibraryPlugin' includes 'JavaPlugin'; both intermediate libraries and end-applications are included!
        project.plugins.withType(JavaLibraryPlugin::class.java).whenPluginAdded {
            project.afterEvaluate {
                if (enabled(project)) {
                    execute(project)
                }
            }
        }
    }

    private fun enabled(project: Project): Boolean {
        val featureName = "feature.slf4j.enable"
        val enableFeatureText: String? = project.findProperty(featureName)?.toString()?.trim()
        val enableFeature = enableFeatureText?.toBoolean() ?: true
        return enableFeature
    }

    private fun execute(project: Project) {
        project.dependencies {
            val apiExists = project.configurations.findByName("api") != null
            if (apiExists) {
                project.logger.debug("Applying 'api' dependencies.")
                add("api", "org.slf4j:slf4j-api")
                add("api", "org.slf4j:slf4j-ext")
            } else {
                project.logger.debug("Applying 'implementation' dependencies.")
                add("implementation", "org.slf4j:slf4j-api")
                add("implementation", "org.slf4j:slf4j-ext")
            }

            if (apiExists) {
                project.logger.debug("Applying 'testImplementation' dependencies.")
                add("testImplementation", "org.slf4j:slf4j-simple")
            }

            constraints {
                SLF4JFeatureVersion().applyConstraints(this)
            }
        }
    }
}
