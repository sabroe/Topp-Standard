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

package com.yelstream.topp.grasp.feature.root

import com.yelstream.topp.grasp.api.Projects
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Root feature plugin.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-11-16
 */
class RootFeaturePlugin : Plugin<Project> {
    companion object {
        const val FEATURE_NAME: String = "Root"
        const val FEATURE_ROOT: String = "feature.root"
        const val ENABLE_FEATURE: String = "$FEATURE_ROOT.enable"
/*
        const val ENABLE_MAIN_PART_FEATURE: String = "$FEATURE_ROOT.part.main.enable"
        const val ENABLE_TEST_PART_FEATURE: String = "$FEATURE_ROOT.part.test.enable"
        const val ENABLE_CONSTRAINTS_PART_FEATURE: String = "$FEATURE_ROOT.part.constraints.enable"
*/

        val FEATURE_PLUGIN_IDS: List<String> = listOf(
//            "com.yelstream.topp.grasp.feature.lombok",
//            "com.yelstream.topp.grasp.feature.slf4j",
//            "com.yelstream.topp.grasp.feature.junit",
            "com.yelstream.topp.grasp.feature.task-tree"
        )

    }

    override fun apply(project: Project) {
project.pluginManager.apply("com.dorongold.task-tree")
        if (Projects.enabled(project,ENABLE_FEATURE).orElse(true)) {
            execute(project)
        }
    }

    private fun execute(project: Project) {
        FEATURE_PLUGIN_IDS.forEach { featurePluginId ->
            project.pluginManager.apply(featurePluginId)
        }
    }
}
