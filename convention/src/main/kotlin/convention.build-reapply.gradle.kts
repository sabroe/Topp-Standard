/*
 * Project: Topp Grasp
 * GitHub: https://github.com/sabroe/Topp-Grasp
 *
 * Copyright 2022-2026 Morten Sabroe Mortensen
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

/*
 * "Build Reapply" convention plugin.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-12-21
 */

import org.gradle.api.plugins.JavaPlugin

plugins {
}

val conventionName = "redo"

project.logger.info("Convention ${conventionName} loaded.")

//afterEvaluate {
    project.plugins.withType<JavaPlugin> {

        val enablePropertyName = "convention.${conventionName}.enable"
        val enable = project.findProperty(enablePropertyName)?.toString()?.trim()?.toBooleanStrictOrNull() ?: true
        if (!enable) {
            project.logger.debug("Convention ${conventionName} disabled.")
        } else {
            project.logger.debug("Convention ${conventionName} enabled.")

            tasks.register("reinitialize") {
                group = "Build Reapply"
                description = "Clean and initialize."
                dependsOn("clean","initialize")
            }
            tasks.register("regenerate") {
                group = "Build Reapply"
                description = "Clean and generate."
                dependsOn("clean","generate")
            }
            tasks.register("recompile") {
                group = "Build Reapply"
                description = "Clean and compile."
                dependsOn("clean","compile")
            }
            tasks.register("reassemble") {
                group = "Build Reapply"
                description = "Clean and assemble."
                dependsOn("clean","assemble")
            }
            tasks.register("recheck") {
                group = "Build Reapply"
                description = "Clean and check."
                dependsOn("clean","check")
            }
            tasks.register("reanalyze") {
                group = "Build Reapply"
                description = "Clean and analyze."
                dependsOn("clean","analyze")
            }
            tasks.register("retest") {
                group = "Build Reapply"
                description = "Clean and test."
                dependsOn("clean","test")
            }
            tasks.register("reverify") {
                group = "Build Reapply"
                description = "Clean and verify."
                dependsOn("clean","verify")
            }
            tasks.register("rebuild") {
                group = "Build Reapply"
                description = "Clean and build."
                dependsOn("clean","build")
            }
            tasks.register("rerun") {
                group = "Build Reapply"
                description = "Clean and run."
                dependsOn("clean","run")
            }
            tasks.register("repackage") {
                group = "Build Reapply"
                description = "Clean and package."
                dependsOn("clean","package")
            }
            tasks.register("redeploy") {
                group = "Build Reapply"
                description = "Clean and deploy."
                dependsOn("clean","deploy")
            }
            tasks.register("republish") {
                group = "Build Reapply"
                description = "Clean and publish."
                dependsOn("clean","publish")
            }
            tasks.register("reinstall") {
                group = "Build Reapply"
                description = "Clean and install."
                dependsOn("clean","install")
            }

        }
    }
//}
