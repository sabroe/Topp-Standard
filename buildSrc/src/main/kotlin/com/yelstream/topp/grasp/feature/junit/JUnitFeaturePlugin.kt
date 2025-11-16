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

package com.yelstream.topp.grasp.feature.junit

import com.yelstream.topp.grasp.api.Projects
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class JUnitFeaturePlugin : Plugin<Project> {
    companion object {
        const val FEATURE_NAME: String = "Lombok"
        const val FEATURE_ROOT: String = "feature.junit"
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
            if (Projects.enabled(project,ENABLE_MAIN_PART_FEATURE).orElse(true)) {
                project.logger.debug("Applying 'api' dependencies.")
            }

            if (Projects.enabled(project,ENABLE_TEST_PART_FEATURE).orElse(true)) {
                project.logger.debug("Applying 'testImplementation' dependencies.")
                add("testImplementation","org.junit.jupiter:junit-jupiter")
                add("testImplementation", "org.junit.jupiter:junit-jupiter-engine")
                add("testImplementation", "org.junit.jupiter:junit-jupiter-api")
                add("testImplementation", "org.junit.jupiter:junit-jupiter-params")
                add("testImplementation", "org.junit.platform:junit-platform-suite")
                add("testImplementation", "org.junit.platform:junit-platform-suite-api")
            }

            if (Projects.enabled(project,ENABLE_CONSTRAINTS_PART_FEATURE).orElse(true)) {
                constraints {
                    JUnitFeatureVersion.applyConstraints(project,this)
                }
            }
        }

        project.tasks.withType<Test> {
            if (Projects.enabled(project, ENABLE_TEST_PART_FEATURE).orElse(true)) {
                useJUnitPlatform()

                testLogging {
                    events = setOf(TestLogEvent.SKIPPED, TestLogEvent.FAILED)
                }

                filter {
                    includeTestsMatching("*TestSuite")
                }
            }
        }
    }
}
