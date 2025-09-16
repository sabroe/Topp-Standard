package com.example.feature

import org.gradle.api.Plugin
import org.gradle.api.Project

class FeatureRootPlugin implements Plugin<Project> {
    private static final List<String> FEATURE_PLUGINS = [
            'com.yelstream.topp.grasp.feature.lombok'//,
            //'com.yelstream.topp.grasp.feature.slf4j'

/*
            'com.example.slf4j-feature',
            'com.example.lombok-feature',
            'com.example.cyclonedx-feature',
            'com.example.junit-feature'
*/
            // Add more feature plugin IDs as you create them
    ]

    void apply(Project project) {
        // Apply only to source code modules (exclude BOM subprojects)
/*
        if (!project.name.startsWith('module:')) {
            return
        }
*/

        // Dynamically apply all feature plugins by default
        FEATURE_PLUGINS.each { featurePluginId ->
            project.pluginManager.apply(featurePluginId)
        }
    }
}
