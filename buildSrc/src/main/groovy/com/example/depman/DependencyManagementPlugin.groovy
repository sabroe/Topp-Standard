package com.example.depman

import org.gradle.api.Plugin
import org.gradle.api.Project

class DependencyManagementPlugin implements Plugin<Project> {
    private static final List<String> FEATURE_BOMS = [
            'gradle/feature/slf4j-bom',
            'gradle/feature/lombok-bom',
            'gradle/feature/cyclonedx-bom',
            'gradle/feature/junit-bom'
            // Add more feature BOM paths as needed
    ]

    void apply(Project project) {
        // Dynamically include all feature BOM subprojects as composite builds
        project.gradle.beforeProject { p ->
            if (p == project.rootProject) {
                FEATURE_BOMS.each { bomPath ->
                    p.gradle.includeBuild(bomPath) {
                        dependencySubstitution {
                            substitute module("com.example:${bomPath.split('/').last()}") with project(":${bomPath.split('/').last()}")
                        }
                    }
                }
            }
        }

        // Apply dependency management with a central BOM or individual BOMs
        project.dependencyManagement {
            imports {
                // Option 1: Import a central aggregating BOM (if you created one)
                // mavenBom "com.example:central-bom:1.0.0" // Or use project(':central-bom') if local

                // Option 2: Import individual feature BOMs directly
                FEATURE_BOMS.each { bomPath ->
                    mavenBom platform(project(":${bomPath.split('/').last()}"))
                }
            }
        }
    }
}
