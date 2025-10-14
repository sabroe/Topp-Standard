package com.yelstream.topp.grasp.resolve.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencyResolveDetails
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.attributes.Attribute
import org.gradle.kotlin.dsl.getByType
import org.tomlj.Toml

class DynamicVersionResolverPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Create a custom extension to track constraint origins (optional)
//        project.extensions.create("constraintOrigins", ConstraintOriginsExtension::class.java)

        project.afterEvaluate {
            project.logger.warn("DynamicVersionResolverPlugin applied to ${project.path}")
            val allConfiguredSuccessfully = project.configurations.all { configuration ->
                project.logger.warn("Found configuration in ${project.path}: ${configuration.name}")
                configureResolutionStrategy(configuration, project)
            }
            if (allConfiguredSuccessfully) {
                project.logger.info("Successfully configured resolution strategies for all configurations")
            } else {
                project.logger.warn("Some configurations failed to apply resolution strategy")
            }
        }
    }

    private fun configureResolutionStrategy(configuration: Configuration, project: Project): Boolean {
        return try {
            configuration.resolutionStrategy.eachDependency {
                val requested = this.requested
                val group = requested.group
                val name = requested.name
                val requestedVersion = requested.version
                val targetVersion = this.target.version
                val constraintInfo = findConstraintInfo(project, configuration, group, name)
                project.logger.debug("Evaluating dependency: ${group}:${name}:${requestedVersion} " +
                        "(target: ${targetVersion}, constraint: ${constraintInfo?.let { "${it.version} (reason: ${it.reason ?: "none"}, plugin: ${it.pluginAttribute ?: "none"})" } ?: "none"})")

                if (needsCustomResolution(this, project, configuration)) {
                    project.logger.warn("Needs custom resolution: ${group}:${name}:${requestedVersion} " +
                            "(constraint: ${constraintInfo?.let { "${it.version} (reason: ${it.reason ?: "none"}, plugin: ${it.pluginAttribute ?: "none"})" } ?: "none"})")
                    val resolvedVersion = resolveFromCustomMechanism(project, group, name)
                    if (resolvedVersion != null) {
                        project.logger.warn("Applying custom version ${resolvedVersion} for ${group}:${name}, " +
                                "overriding constraint: ${constraintInfo?.let { "${it.version} (reason: ${it.reason ?: "none"}, plugin: ${it.pluginAttribute ?: "none"})" } ?: "none"}")
                        this.useVersion(resolvedVersion)
                        this.because("Set by DynamicVersionResolverPlugin, overriding constraint: ${constraintInfo?.reason ?: "none"}")
                    } else {
                        project.logger.warn("No custom version found for ${group}:${name}; leaving unresolved")
                    }
                } else {
                    project.logger.debug("Skipping custom resolution for ${group}:${name}; " +
                            "version already set: ${targetVersion ?: requestedVersion}, " +
                            "constraint: ${constraintInfo?.let { "${it.version} (reason: ${it.reason ?: "none"}, plugin: ${it.pluginAttribute ?: "none"})" } ?: "none"}")
                }
            }
            true
        } catch (e: Exception) {
            project.logger.error("Error configuring resolution strategy for ${configuration.name}: ${e.message}")
            false
        }
    }

    private fun needsCustomResolution(details: DependencyResolveDetails, project: Project, configuration: Configuration): Boolean {
        val requestedVersion = details.requested.version
        val targetVersion = details.target.version
        val constraintInfo = findConstraintInfo(project, configuration, details.requested.group, details.requested.name)
        project.logger.warn("Constraint info for ${details.requested.group}:${details.requested.name}: ${constraintInfo ?: "none"}")
/*
        val extensionOrigin = project.extensions.findByType(ConstraintOriginsExtension::class.java)
            ?.getOrigin(details.requested.group, details.requested.name)
*/
//        project.logger.debug("Extension origin for ${details.requested.group}:${details.requested.name}: ${extensionOrigin ?: "none"}")

        // Override org.slf4j constraints if set by SLF4JFeaturePlugin (via reason, attribute, or extension)
        val shouldOverride = constraintInfo?.reason?.contains("SLF4JFeaturePlugin") ?: false ||
                constraintInfo?.pluginAttribute == "SLF4JFeaturePlugin" /*||
                extensionOrigin == "SLF4JFeaturePlugin"*/
        return details.requested.group == "org.slf4j" && shouldOverride ||
                (requestedVersion.isNullOrEmpty() && targetVersion.isNullOrEmpty())
    }

    private data class ConstraintInfo(val version: String, val reason: String?, val pluginAttribute: String?)

    private fun findConstraintInfo(project: Project, configuration: Configuration, group: String, name: String): ConstraintInfo? {
        val constraint = configuration.dependencyConstraints.find { it.group == group && it.name == name }
        return constraint?.let {
            val pluginAttribute = it.attributes.getAttribute(Attribute.of("com.example.plugin", String::class.java))
            ConstraintInfo(it.version ?: "unspecified", it.reason, pluginAttribute)
        }
    }

    private fun resolveFromCustomMechanism(project: Project, group: String, name: String): String? {
        project.logger.debug("Resolving version for $group:$name")
        // Check Gradle version catalog
        val catalogVersion = try {
            val catalogs = project.extensions.getByType<VersionCatalogsExtension>()
            val catalog = catalogs.named("libs") // Assumes default catalog name 'libs'
            catalog.findVersion("$group.$name").orElse(null)?.requiredVersion
        } catch (e: Exception) {
            project.logger.debug("No version found in catalog for $group:$name: ${e.message}")
            null
        }
        if (catalogVersion != null) {
            project.logger.warn("Resolved from version catalog: $group:$name -> $catalogVersion")
            return catalogVersion
        }

        // Check custom versions.toml
        val tomlFile = project.file("versions.toml")
        if (tomlFile.exists()) {
            try {
                val toml = Toml.parse(tomlFile.toPath())
                val version = toml.getString("dependencies.$group.$name")
                if (version != null) {
                    project.logger.warn("Resolved from custom TOML: $group:$name -> $version")
                    return version
                }
            } catch (e: Exception) {
                project.logger.error("Failed to parse versions.toml: ${e.message}")
            }
        }

        // Fallback: Hardcoded mapping
        val versionMap = mapOf(
            "org.slf4j:slf4j-api" to "2.1.0-alpha1",
            "org.slf4j:slf4j-ext" to "2.1.0-alpha1",
            "org.slf4j:slf4j-simple" to "2.1.0-alpha1",
            "io.vertx:vertx-core" to "5.0.4"
        )
        val version = versionMap["$group:$name"]
        if (version != null) {
            project.logger.warn("Resolved from mapping: $group:$name -> $version")
        } else {
            project.logger.debug("No version found for $group:$name")
        }
        return version
    }
}

// Optional: Extension to track constraint origins
/*
open class ConstraintOriginsExtension {
    private val origins = mutableMapOf<String, String>()

    fun recordOrigin(group: String, name: String, origin: String) {
        origins["$group:$name"] = origin
    }

    fun getOrigin(group: String, name: String): String? {
        return origins["$group:$name"]
    }
}
*/

/*
configuration.resolutionStrategy.componentSelection.all { selection ->
    if (selection.candidate.group == "org.slf4j" && selection.candidate.module == "slf4j-api" && selection.candidate.version.isEmpty()) {
        val version = resolveFromCustomMechanism(project, selection.candidate.group, selection.candidate.module)
        if (version != null) {
            // Cannot set version directly; use resolutionStrategy to force it
            configuration.resolutionStrategy.force("${selection.candidate.group}:${selection.candidate.module}:${version}")
        }
    }
}
 */

/*
versions.toml

[dependencies.org.slf4j]
slf4j-api = "2.1.0-alpha1"
slf4j-ext = "2.1.0-alpha1"
slf4j-simple = "2.1.0-alpha1"

[dependencies.io.vertx]
vertx-core = "5.0.4"
 */


/*
tasks.register("inspectConstraints") {
    doLast {
        configurations.forEach { config ->
            println("Configuration: ${config.name}")
            config.dependencyConstraints.forEach { constraint ->
                println("${constraint.group}:${constraint.name}:${constraint.version} " +
                        "(reason: ${constraint.reason}, plugin: ${constraint.attributes.getAttribute(Attribute.of("com.example.plugin", String))})")
            }
        }
    }
}
*/
