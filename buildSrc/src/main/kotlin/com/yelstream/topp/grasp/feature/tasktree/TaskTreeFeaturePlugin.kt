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

package com.yelstream.topp.grasp.feature.tasktree

import com.yelstream.topp.grasp.api.Projects
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.net.URI

/**
 * Task-Tree feature plugin.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-11-16
 */
class TaskTreeFeaturePlugin : Plugin<Project> {
    companion object {
        const val FEATURE_NAME: String = "Task-Tree"
        const val FEATURE_ROOT: String = "feature.task-tree"
        const val ENABLE_FEATURE: String = "$FEATURE_ROOT.enable"
/*
        const val ENABLE_MAIN_PART_FEATURE: String = "$FEATURE_ROOT.part.main.enable"
        const val ENABLE_TEST_PART_FEATURE: String = "$FEATURE_ROOT.part.test.enable"
        const val ENABLE_CONSTRAINTS_PART_FEATURE: String = "$FEATURE_ROOT.part.constraints.enable"
*/
    }

    override fun apply(project: Project) {
project.pluginManager.apply("com.dorongold.task-tree")
        project.afterEvaluate {
            if (Projects.enabled(project,ENABLE_FEATURE).orElse(true)) {
                execute(project)
            }
        }
    }

    private fun execute(project: Project) {
//        project.pluginManager.apply("com.dorongold.task-tree")
/*
        project.buildscript.repositories.maven {
            url = URI("https://plugins.gradle.org/m2/")
        }

//        project.buildscript.dependencies.add("classpath", "com.dorongold.plugins:task-tree:4.0.1")
        project.buildscript.dependencies.add("classpath", "com.dorongold:gradle-task-tree:4.0.1")
        project.buildscript.dependencies.add("classpath", "org.apache.commons:commons-lang3:3.19.0")  //Note: To address vulnerability!
//        project.pluginManager.apply("com.dorongold.task-tree")
        project.pluginManager.apply("com.dorongold.gradle.tasktree.TaskTreePlugin")
*/

/*
        project.apply {
            from("C:\\Project\\Topp-Work\\Topp-Standard\\buildSrc\\src\\main\\resources\\com\\yelstream\\topp\\grasp\\feature\\tasktree\\TaskTreeFeatureScript.gradle.kts")
        }
*/
    }
}
