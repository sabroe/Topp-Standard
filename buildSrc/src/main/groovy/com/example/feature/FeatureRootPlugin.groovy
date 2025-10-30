
package com.example.feature

import org.gradle.api.Plugin
import org.gradle.api.Project

class FeatureRootPlugin implements Plugin<Project> {
    private static final List<String> FEATURE_PLUGINS = [
        'com.yelstream.topp.grasp.feature.lombok',
        'com.yelstream.topp.grasp.feature.slf4j'
    ]

    void apply(Project project) {
        FEATURE_PLUGINS.each { featurePluginId ->
            project.pluginManager.apply(featurePluginId)
        }
    }
}
