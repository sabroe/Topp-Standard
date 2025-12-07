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

package com.yelstream.topp.grasp.feature.slf4j

import com.yelstream.topp.grasp.api.Projects
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
    companion object {
        const val FEATURE_NAME: String = "SLF4J"
        const val FEATURE_ROOT: String = "feature.slf4j"
        const val ENABLE_FEATURE: String = "$FEATURE_ROOT.enable"
        const val ENABLE_MAIN_PART_FEATURE: String = "$FEATURE_ROOT.part.main.enable"
        const val ENABLE_TEST_PART_FEATURE: String = "$FEATURE_ROOT.part.test.enable"
        const val ENABLE_CONSTRAINTS_PART_FEATURE: String = "$FEATURE_ROOT.part.constraints.enable"
    }

    override fun apply(project: Project) {
        //Note: 'JavaLibraryPlugin' includes 'JavaPlugin'; both intermediate libraries and end-applications are included!
        project.plugins.withType(JavaLibraryPlugin::class.java).whenPluginAdded {
            project.afterEvaluate {
                if (Projects.enabled(project,ENABLE_FEATURE).orElse(true)) {
                    execute(project)
                }
            }
        }
    }

    private fun execute(project: Project) {
        project.dependencies {
            val apiExists = project.configurations.findByName("api") != null
            if (Projects.enabled(project,ENABLE_MAIN_PART_FEATURE).orElse(true)) {
                if (apiExists) {
                    project.logger.debug("Applying 'api' dependencies.")
                    add("api", "org.slf4j:slf4j-api")
                    add("api", "org.slf4j:slf4j-ext")
                } else {
                    project.logger.debug("Applying 'implementation' dependencies.")
                    add("implementation", "org.slf4j:slf4j-api")
                    add("implementation", "org.slf4j:slf4j-ext")
                }
            }

            if (Projects.enabled(project,ENABLE_TEST_PART_FEATURE).orElse(true)) {
                if (apiExists) {
                    project.logger.debug("Applying 'testImplementation' dependencies.")
                    add("testImplementation", "org.slf4j:slf4j-simple")
                }
            }

            if (Projects.enabled(project,ENABLE_CONSTRAINTS_PART_FEATURE).orElse(true)) {
                constraints {
                    SLF4JFeatureVersion.applyConstraints(project,this)
                }
            }
        }
    }
}
