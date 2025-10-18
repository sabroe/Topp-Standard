package com.yelstream.topp.grasp.feature.slf4j;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.attributes.Attribute

class SLF4JFeaturePlugin implements Plugin<Project> {
    void apply(Project project) {
        project.plugins.withType(JavaLibraryPlugin).configureEach {  //Note: 'JavaPlugin' includes 'JavaLibraryPlugin'; both intermediate libraries and end-applications are included!
//            project.logger.debug("[${buildscript.sourceFile.name}]:> Plugins applied: ${plugins*.class.simpleName}")
//            def enableFeatureText = custom['feature.slf4j.enable']?.toString()?.trim()
            def enableFeatureText = project.hasProperty('feature.slf4j.enable') ? project.property('feature.slf4j.enable').toString().trim() : null
            def enableFeature = enableFeatureText ? enableFeatureText.toBoolean() : true
            if (!enableFeature) {
                project.logger.debug("Feature 'SLF4J' is disabled.")
            } else {
                project.dependencies {
                    project.logger.debug("Available configurations: ${project.configurations.names}")
                    if (project.configurations.named('api')) {
                        //Use 'api' if available (implies java-library plugin is applied)
                        project.logger.debug("Applying 'api' dependencies.")
                        api 'org.slf4j:slf4j-api'  //TODO: 2.1.0-alpha1 per 2024-02-01!
                        api 'org.slf4j:slf4j-ext'
                    } else {
                        //Fallback to 'implementation' (implies only java plugin is applied)
                        project.logger.debug("Applying 'implementation' dependencies.")
                        implementation 'org.slf4j:slf4j-api'
                        implementation 'org.slf4j:slf4j-ext'
                    }

                    //Apply testImplementation only for intermediate libraries
                    if (project.configurations.named('api')) {
                        project.logger.debug("Applying 'testImplementation' dependencies.")
                        testImplementation 'org.slf4j:slf4j-simple'
                    }

// Apply default versions via constraints only if DynamicVersionResolverPlugin is not present
//if (!project.plugins.hasPlugin('com.example.dynamic-version-resolver')) {
//                        project.logger.debug("[${buildscript.sourceFile.name}]:> Applying SLF4J default version constraints (DynamicVersionResolverPlugin not present).")
                    project.logger.warn("Applying SLF4J default version constraints (DynamicVersionResolverPlugin not present).")
                    constraints {
                        implementation('org.slf4j:slf4j-api:6.0.17') {
                            because 'Default version set by SLF4JFeaturePlugin'
                            attributes {
                                attribute(Attribute.of('com.example.plugin', String), 'SLF4JFeaturePlugin')
                            }
                        }
                        implementation('org.slf4j:slf4j-ext:6.0.17') {
                            because 'Default version set by SLF4JFeaturePlugin'
                            attributes {
                                attribute(Attribute.of('com.example.plugin', String), 'SLF4JFeaturePlugin')
                            }
                        }
                        testImplementation('org.slf4j:slf4j-simple:6.0.17') {
                            because 'Default version set by SLF4JFeaturePlugin'
                            attributes {
                                attribute(Attribute.of('com.example.plugin', String), 'SLF4JFeaturePlugin')
                            }
                        }
                    }
                }
            }
        }
    }
}
