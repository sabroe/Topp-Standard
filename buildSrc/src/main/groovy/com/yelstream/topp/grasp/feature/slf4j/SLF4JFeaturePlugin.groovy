package com.yelstream.topp.grasp.feature.slf4j;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin

class SLF4JFeaturePlugin implements Plugin<Project> {
    void apply(Project project) {
        project.plugins.withType(JavaLibraryPlugin).configureEach {  //Note: 'JavaPlugin' includes 'JavaLibraryPlugin'; both intermediate libraries and end-applications are included!
            logger.debug("[${buildscript.sourceFile.name}]:> Plugins applied: ${plugins*.class.simpleName}")
            def enableFeatureText = custom['feature.slf4j.enable']?.toString()?.trim()
            def enableFeature = enableFeatureText ? enableFeatureText.toBoolean() : true
            if (!enableFeature) {
                logger.debug("Feature 'SLF4J' is disabled.")
            } else {
                dependencies {
                    logger.debug("[${buildscript.sourceFile.name}]:> Available configurations: ${configurations.names}")
                    if (configurations.findByName('api')) {
                        //Use 'api' if available (implies java-library plugin is applied)
                        logger.debug("[${buildscript.sourceFile.name}]:> Applying 'api' dependencies.")
                        api 'org.slf4j:slf4j-api:2.0.17'  //TODO: 2.1.0-alpha1 per 2024-02-01!
                        api 'org.slf4j:slf4j-ext:2.0.17'
                    } else {
                        //Fallback to 'implementation' (implies only java plugin is applied)
                        logger.debug("[${buildscript.sourceFile.name}]:> Applying 'implementation' dependencies.")
                        implementation 'org.slf4j:slf4j-api:2.0.17'
                        implementation 'org.slf4j:slf4j-ext:2.0.17'
                    }

                    //Apply testImplementation only for intermediate libraries
                    if (configurations.findByName('api')) {
                        logger.debug("[${buildscript.sourceFile.name}]:> Applying 'testImplementation' dependencies.")
                        testImplementation 'org.slf4j:slf4j-simple:2.0.17'
                    }
                }
            }
        }
    }
}
