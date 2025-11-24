buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.dorongold.task-tree:com.dorongold.task-tree.gradle.plugin:4.0.1")
    }
}

import com.dorongold.gradle.tasktree.TaskTree

plugins {
    id("com.dorongold.task-tree") version "4.0.1"
}

tasks.named<TaskTree>("taskTree").configure {
    depth = 3  // Limit tree depth (default is unlimited)
    withInputs = true  // Show task inputs (in red)
    withOutputs = true  // Show task outputs (in green)
    withDescription = true  // Show task descriptions (in orange)
    repeat = true  // Allow repeated sub-trees
    impliesSubProjects = true  // Include subproject implications
}
