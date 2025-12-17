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
 * Lombok convention plugin.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-12-16
 */

import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.apply
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.TaskContainer

plugins {
    id("java") apply false  //Note: Required for type-safe dependency accessors!
    id("org.cyclonedx.bom") apply false  //See https://plugins.gradle.org/plugin/org.cyclonedx.bom
}

val conventionName = "cyclonedx"

project.logger.info("Convention ${conventionName} loaded.")

project.plugins.withType<JavaPlugin> {
    val enablePropertyName = "convention.${conventionName}.enable"
    val enable = project.findProperty(enablePropertyName)?.toString()?.trim()?.toBooleanStrictOrNull()?:true
    if (!enable) {
        project.logger.debug("Convention ${conventionName} disabled.")
    } else {
        project.logger.debug("Convention ${conventionName} enabled.")

        apply(plugin = "org.cyclonedx.bom")

/*        //Configure the per-project BOM generation task (cyclonedxDirectBom)
        tasks.named("cyclonedxDirectBom") {
            // List configurations to include/exclude (supports regex)
            includeConfigs = listOf("runtimeClasspath")
            skipConfigs = listOf("compileClasspath", "testCompileClasspath")

            // Skip aggregating the root project itself (useful in subprojects)
            skipProjects = listOf(rootProject.name)

            projectType = "library"  // or "application"

            // Custom output directory and filenames for both XML and JSON
            val outputDir = layout.buildDirectory.dir("reports/cyclonedx-direct").get().asFile
            jsonOutput.set(outputDir.resolve("bom.json"))
            xmlOutput.set(outputDir.resolve("bom.xml"))
        }
*/

        // Optional: if you want an aggregate BOM at root level, apply the plugin there too
        // and configure cyclonedxBom similarly (but usually not needed in leaf projects)
    }
}

/*
 * Note:
 *     To inspect the content of build BOM files, consider this command for ad hoc inspection:
 *     find . -name bom.* | grep -E "(.*)/build/reports/(.*)(bom.xml|bom.json)" | while read r; do echo; echo $r; head -n30 $r; done | less
 */
