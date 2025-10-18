package com.example.feature

import org.gradle.api.Plugin
import org.gradle.api.Project

class FeatureRootPlugin implements Plugin<Project> {
    private static final List<String> FEATURE_PLUGINS = [
        'com.yelstream.topp.grasp.feature.lombok',
        'com.yelstream.topp.grasp.feature.slf4j'
    ]

    void apply(Project project) {
        // Apply only to source code modules (exclude BOM subprojects)
/*
        if (!project.name.startsWith('module:')) {
            return
        }
*/

        FEATURE_PLUGINS.each { featurePluginId ->
            project.pluginManager.apply(featurePluginId)
        }
    }
}
