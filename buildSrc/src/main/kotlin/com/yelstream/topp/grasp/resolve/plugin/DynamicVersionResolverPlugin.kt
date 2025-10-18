package com.yelstream.topp.grasp.resolve.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencyResolveDetails
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.attributes.Attribute
import org.gradle.kotlin.dsl.getByType
import org.slf4j.MarkerFactory
import org.slf4j.spi.LoggingEventBuilder
import org.tomlj.Toml
import org.slf4j.LoggerFactory

class DynamicVersionResolverPlugin : Plugin<Project> {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(DynamicVersionResolverPlugin::class.java)
        private val MARKER_NAME = DynamicVersionResolverPlugin::class.simpleName
        private val MARKER = MarkerFactory.getMarker(MARKER_NAME)

        private fun log(project: Project): org.gradle.api.logging.Logger {
            return project.logger
        }

        private fun log(project: Project, eventBuilderMapper: (org.gradle.api.logging.Logger) -> LoggingEventBuilder) {
            val logger = log(project)
            val leb = eventBuilderMapper(logger)
            decorateEvent(project,leb)
            leb.log()
        }

        private fun decorateEvent(project: Project, leb: LoggingEventBuilder): Unit {
            leb.addMarker(MARKER)
            leb.addKeyValue("project.name",project.name)
//            leb.addKeyValue("project.path",project.path)
        }
    }

    override fun apply(project: Project) {
        log(project) { logger -> logger.atDebug().setMessage("Plugin applied to project; project name is '{}'.").addArgument(project.name) }
        project.afterEvaluate {
            log(project) { logger -> logger.atDebug().setMessage("Plugin applied to project after evaluation.") }
            val status = project.configurations.all { configuration -> configureResolutionStrategy(project,configuration) }
            log(project) { logger -> logger.atInfo().setMessage("Configured resolution strategies for all configurations; status for all successful configuration is '{}'.").addArgument(status) }
        }
    }

    private fun configureResolutionStrategy(project: Project, configuration: Configuration): Boolean {
        log(project) { logger -> logger.atDebug().setMessage("Configuration of resolution strategy; project name is '{}', configuration name is '{}'.").addArgument(project.name).addArgument(configuration.name) }
        return try {
            configuration.resolutionStrategy.eachDependency {
                val requested = this.requested
                val group = requested.group
                val name = requested.name
                val requestedVersion = requested.version
                val targetVersion = this.target.version
                val constraintInfo = findConstraintInfoDebug(project, configuration, group, name)
                log(project) { logger -> logger.atDebug().setMessage("Evaluating dependency: ${group}:${name}:${requestedVersion} " +
                        "(target: ${targetVersion}, constraint: ${constraintInfo?.let { "${it.version} (reason: ${it.reason ?: "none"}, plugin: ${it.pluginAttribute ?: "none"})" } ?: "none"})") }

                if (needsCustomResolution(this, project, configuration)) {
                    log(project) { logger -> logger.atDebug().setMessage("Needs custom resolution: ${group}:${name}:${requestedVersion} " +
                            "(constraint: ${constraintInfo?.let { "${it.version} (reason: ${it.reason ?: "none"}, plugin: ${it.pluginAttribute ?: "none"})" } ?: "none"})") }
                    val resolvedVersion = resolveFromCustomMechanism(project, group, name)
                    if (resolvedVersion != null) {
                        log(project) { logger -> logger.atDebug().setMessage("Applying custom version ${resolvedVersion} for ${group}:${name}, " +
                                "overriding constraint: ${constraintInfo?.let { "${it.version} (reason: ${it.reason ?: "none"}, plugin: ${it.pluginAttribute ?: "none"})" } ?: "none"}") }
                        this.useVersion(resolvedVersion)
                        this.because("Set by DynamicVersionResolverPlugin, overriding constraint: ${constraintInfo?.reason ?: "none"}")
                    } else {
                        log(project) { logger -> logger.atDebug().setMessage("No custom version found for ${group}:${name}; leaving unresolved") }
                    }
                } else {
                    log(project) { logger -> logger.atDebug().setMessage("Skipping custom resolution for ${group}:${name}; " +
                            "version already set: ${targetVersion ?: requestedVersion}, " +
                            "constraint: ${constraintInfo?.let { "${it.version} (reason: ${it.reason ?: "none"}, plugin: ${it.pluginAttribute ?: "none"})" } ?: "none"}") }
                }
            }
            true
        } catch (ex: Exception) {
            log(project) { logger -> logger.atError().setMessage("Error configuring resolution strategy for ${configuration.name}").setCause(ex) }
            false
        }
    }

    private fun needsCustomResolution(details: DependencyResolveDetails, project: Project, configuration: Configuration): Boolean {
        val requestedVersion = details.requested.version
        val targetVersion = details.target.version
        val constraintInfo = findConstraintInfoDebug(project, configuration, details.requested.group, details.requested.name)
        log(project) { logger -> logger.atDebug().setMessage("Constraint info for ${details.requested.group}:${details.requested.name}: ${constraintInfo ?: "none"}") }
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

    private fun findConstraintInfoDebug(project: Project, configuration: Configuration, group: String, name: String): ConstraintInfo? {
        log(project) { logger -> logger.atDebug().setMessage("Finding constraints; project name is '{}', configuration name is '{}', group is '{}', name is '{}'.").addArgument(project.name).addArgument(configuration.name).addArgument(group).addArgument(name) }

        log(project) { logger -> logger.atDebug().setMessage("Direct set of dependency constraints are '{}'.").addArgument(configuration.dependencyConstraints.stream().map{"("+it.toString()+")"}.toList()) }
        log(project) { logger -> logger.atDebug().setMessage("Complete set of dependency constraints are '{}'.").addArgument(configuration.allDependencyConstraints.stream().map{"("+it.toString()+")"}.toList()) }
//        log(project) { logger -> logger.atDebug().setMessage("Complete set of dependency constraints are '{}'.").addArgument(configuration.allDependencyConstraints.stream().map{"("+it.group+","+it.name+","+it.version+","+it.reason+","+it.attributes+")"}.toList()) }

        val constraint = configuration.allDependencyConstraints.find { it.group == group && it.name == name }
if (constraint!=null) {
    log(project) { logger -> logger.atDebug().setMessage("############### CONSTRAINT: ${configuration.name} for $group:$name " + constraint) }
}
        if (constraint != null) {
            val pluginAttribute = constraint.attributes.getAttribute(Attribute.of("com.example.plugin", String::class.java))
            log(project) { logger -> logger.atDebug().setMessage("Found constraint in ${configuration.name}: ${constraint.group}:${constraint.name}:${constraint.version} " +
                    "(reason: ${constraint.reason}, plugin: ${pluginAttribute ?: "none"})") }
            return ConstraintInfo(constraint.version ?: "unspecified", constraint.reason, pluginAttribute)
        }

        log(project) { logger -> logger.atDebug().setMessage("No constraint found for $group:$name in configuration ${configuration.name} or related configurations") }
        return null
    }

    private fun resolveFromCustomMechanism(project: Project, group: String, name: String): String? {
        log(project) { logger -> logger.atDebug().setMessage("Resolving version for $group:$name") }
        // Check Gradle version catalog
        val catalogVersion = try {
            val catalogs = project.extensions.getByType<VersionCatalogsExtension>()
            val catalog = catalogs.named("libs") // Assumes default catalog name 'libs'
            catalog.findVersion("$group.$name").orElse(null)?.requiredVersion
        } catch (e: Exception) {
            log(project) { logger -> logger.atDebug().setMessage("No version found in catalog for $group:$name: ${e.message}") }
            null
        }
        if (catalogVersion != null) {
            log(project) { logger -> logger.atDebug().setMessage("Resolved from version catalog: $group:$name -> $catalogVersion") }
            return catalogVersion
        }

        // Check custom versions.toml
        val tomlFile = project.file("versions.toml")
        if (tomlFile.exists()) {
            try {
                val toml = Toml.parse(tomlFile.toPath())
                val version = toml.getString("dependencies.$group.$name")
                if (version != null) {
                    log(project) { logger -> logger.atDebug().setMessage("Resolved from custom TOML: $group:$name -> $version") }
                    return version
                }
            } catch (e: Exception) {
                log(project) { logger -> logger.atError().setMessage("Failed to parse versions.toml: ${e.message}") }
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
            log(project) { logger -> logger.atDebug().setMessage("Resolved from mapping: $group:$name -> $version") }
        } else {
            log(project) { logger -> logger.atDebug().setMessage("No version found for $group:$name") }
        }
        return version
    }
}

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
