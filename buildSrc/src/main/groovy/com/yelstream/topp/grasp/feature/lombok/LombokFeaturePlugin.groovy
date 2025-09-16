package com.yelstream.topp.grasp.feature.lombok;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.plugins.JavaPlugin

class LombokFeaturePlugin implements Plugin<Project> {
    void apply(Project project) {
        Logger logger=project.getLogger()
        project.plugins.withType(JavaPlugin).configureEach {  //Note: 'JavaPlugin' includes 'JavaLibraryPlugin'; both intermediate libraries and end-applications are included!
//            def enableFeatureText = custom['feature.lombok.enable']?.toString()?.trim()
            def enableFeatureText = project.hasProperty('feature.lombok.enable') ? project.property('feature.lombok.enable').toString().trim() : null
            def enableFeature = enableFeatureText ? enableFeatureText.toBoolean() : true
            if (!enableFeature) {
                logger.debug("Feature 'Lombok' is disabled.")
            } else {
                def dependencies= project.getDependencies()
                dependencies.add('compileOnly', 'org.projectlombok:lombok:1.18.40')
                dependencies.add('annotationProcessor','org.projectlombok:lombok:1.18.40')

                dependencies.add('testCompileOnly','org.projectlombok:lombok:1.18.40')
                dependencies.add('testAnnotationProcessor','org.projectlombok:lombok:1.18.40')

/*
            compileOnly 'org.projectlombok:lombok:edge-SNAPSHOT'
            annotationProcessor 'org.projectlombok:lombok:edge-SNAPSHOT'

            testCompileOnly 'org.projectlombok:lombok:edge-SNAPSHOT'
            testAnnotationProcessor 'org.projectlombok:lombok:edge-SNAPSHOT'
*/
            }
        }
    }
}
