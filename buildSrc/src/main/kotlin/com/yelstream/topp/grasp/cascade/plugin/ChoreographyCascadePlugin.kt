/*
 * Project: Topp Grasp
 * GitHub: https://github.com/sabroe/Topp-Grasp
 *
 * Copyright 2022-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.grasp.cascade.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.extra
import java.io.File
import java.util.*

object Constants {
    const val MODULE_KEY = "module"
}

open class Module(private val project: Project) : ExtensionAware {
    private val internalProperties = mutableMapOf<String, Any?>()

    override fun getExtensions() = project.extensions

    open fun setProperty(name: String, value: Any?) {
        internalProperties[name] = value
    }

    open fun getProperty(name: String): Any? {
        return internalProperties[name] ?: throw IllegalArgumentException("Property '$name' not found in module")
    }

    open fun findProperty(name: String): Any? {
        return internalProperties[name]
    }

    open fun configureProperties(configure: MutableMap<String, Any?>.() -> Unit) {
        internalProperties.configure()
    }

    /**
     * For each key in this project's `local.properties`: the value the project saw before the file was applied.
     * Child projects use it to hide this project's local values from Gradle's lookup in parent projects.
     */
    val localPrevious = mutableMapOf<String, Any?>()

    /**
     * True once the CC-plugin has configured this project.
     * The upper-most application on a project's path configures it; an application further down skips it.
     */
    var configured: Boolean = false

    var description: String? = null

    val extra: ExtraPropertiesExtension
        get() = project.extensions.extraProperties

    val properties: Map<String, Any?>
        get() = internalProperties.toMap()
}

val Project.module: Module
    get() = if (extensions.extraProperties.has(Constants.MODULE_KEY)) {
        extensions.extraProperties[Constants.MODULE_KEY] as Module
    } else {
        Module(this).also {
            extensions.extraProperties[Constants.MODULE_KEY] = it
        }
    }

/**
 * True if the CC-plugin has configured this project.
 * Doesn't create anything on the project, so it is safe to ask about a project outside the plugin's area.
 */
val Project.isCascadeConfigured: Boolean
    get() = extensions.extraProperties.has(Constants.MODULE_KEY) && module.configured

/**
 * Gives this project's holder with the given name, and creates it as an empty [Properties] if it doesn't exist.
 * An existing holder is left as it is.
 */
private fun Project.holder(name: String): Properties {
    val extra = extensions.extraProperties
    return (if (extra.has(name)) extra[name] as? Properties else null) ?: Properties().also { extra[name] = it }
}

val Project.rawModule: Properties
    get() = holder("rawModule")

val Project.custom: Properties
    get() = holder("custom")

val Project.local: Properties
    get() = holder("local")

/**
 * Overrides an existing property of this project.
 * A property inherited from a parent project, e.g. from the parent's `gradle.properties`, cannot be set on
 * this project; it is shadowed by an extra property on this project instead.
 */
fun Project.overrideProperty(name: String, value: Any?) {
    try {
        setProperty(name, value)
    } catch (_: groovy.lang.MissingPropertyException) {
        extensions.extraProperties.set(name, value)
    }
}

fun Project.loadProperties(fileName: String): Properties {
    val file = file(fileName)
    return loadProperties(file)
}

fun Project.loadProperties(file: File): Properties {
    val properties = Properties()
    logger.info("[${name}]:> Looking for properties file: $file")
    if (file.exists()) {
        file.reader().use { properties.load(it) }
        logger.info("[${name}]:> Did find and read properties file: $file")
    }
    return properties
}

class PropertyInheritBuilder(private val projectAccessor: (Project) -> Properties,
                             private val parentAccessor: (Project) -> Properties) {
    fun inherit(project: Project, properties: Properties) {
        // A parent outside the plugin's area has no holders, and counts as empty.
        project.parent?.takeIf { it.isCascadeConfigured }?.let { parent ->
            projectAccessor(project).putAll(parentAccessor(parent))
        }
        projectAccessor(project).putAll(properties)
    }
    fun inherit(project: Project, fileName: String) {
        val properties = project.loadProperties(fileName)
        inherit(project,properties)
    }
}

fun Project.inheritPropertiesFromFile(
    fileName: String,
    projectAccessor: (Project) -> Properties,
    parentAccessor: (Project) -> Properties
) {
    val builder = PropertyInheritBuilder(projectAccessor, parentAccessor)
    builder.inherit(this, fileName)
}

fun Properties.resolvePlaceholders(project: Project, sources: Properties): Properties {
    val resolved = Properties()
//    val visited = mutableSetOf<String>()

    fun resolveValue(value: String, keyChain: Set<String> = emptySet()): String {
        if (!value.contains("\${") || keyChain.contains(value)) return value
        return value.replace(Regex("\\$\\{([^}]+)\\}")) { match ->
            val propKey = match.groupValues[1]
            if (propKey in keyChain) return@replace match.value
            val resolvedValue = sources.getProperty(propKey)
                ?: sources.getProperty("default.$propKey")
                ?: project.findProperty(propKey)?.toString()
                ?: project.findProperty("default.$propKey")?.toString()
                ?: System.getProperty(propKey)
                ?: System.getProperty("default.$propKey")
                ?: match.value
            resolveValue(resolvedValue, keyChain + propKey)
        }
    }

    forEach { key, value ->
        resolved[key] = resolveValue(value.toString(), setOf(key.toString()))
    }
    return resolved
}

class ChoreographyCascadePlugin : Plugin<Project> {
    companion object {
        const val PROPERTY_NAME_PREFIX = "plugin.choreography-cascade"

        const val ENABLE_PROPERTY_NAME = "enable"
        const val DEFAULT_DELAY = 100L

        const val BUILD_TIME_TICK_PROPERTY_NAME = "build-time.tick"
        const val DEFAULT_BUILD_TIME_TICK = "PT3M"

        const val BUILD_TIME_ZONE_PROPERTY_NAME = "build-time.zone"
    }

    override fun apply(project: Project) {
        // The plugin's area is this project and every project below it (§8 of the documentation).
        // The upper-most application on a project's path configures it. If an application above this one
        // has already configured this project, it has configured everything below it too: nothing to do.
        if (project.isCascadeConfigured) {
            project.logger.info("[${project.name}]:> Already configured by an application of the plugin above; skipped.")
            return
        }

        // Projects below that are already configured were configured by an application further down, which
        // ran first. That happens only if a child's build script is evaluated before its parent's, for
        // example with evaluationDependsOn. They keep that configuration, including its build time.
        val (alreadyConfigured, area) = (listOf(project) + project.subprojects).partition { it.isCascadeConfigured }
        if (alreadyConfigured.isNotEmpty()) {
            project.logger.warn(
                "[${project.name}]:> The CC-plugin was applied further down before it was applied here. " +
                "These projects keep that configuration: ${alreadyConfigured.joinToString { it.path }}"
            )
        }

        // The build time is computed here, once per area, by the upper-most application, and shared by all
        // projects in the area.
        // It is obtained from a value source at configuration time, which makes it a configuration-cache
        // input: Gradle obtains it again on each build and reuses a cache entry only while it is unchanged.
        // The Gradle property "build-time.tick" (an ISO-8601 duration such as PT1H) rounds it down,
        // so a cache entry can be reused within each step. If the property is unset, the default
        // tick (3 minutes) is used. Set it to an empty value to turn rounding off.
        // The Gradle property "build-time.zone" (a time zone ID such as UTC) sets the time zone;
        // if it is unset, the JVM's default time zone is used. The zone is part of the cache input too.
        // Do not keep it in a static (object or companion) value: Gradle keeps buildSrc classes
        // loaded between builds in the same daemon, so a static value would be reused by later builds.
        val buildZonedTime = project.providers.of(BuildTimeValueSource::class.java) {
            parameters.tick.set(
                project.providers.gradleProperty(BUILD_TIME_TICK_PROPERTY_NAME).orElse(DEFAULT_BUILD_TIME_TICK)
            )
            parameters.zone.set(project.providers.gradleProperty(BUILD_TIME_ZONE_PROPERTY_NAME))
        }.get()
        val buildTime = BuildTime(buildZonedTime)

        // Make sure the holders exist on every project in the area. Existing holders are left as they are.
        area.forEach { ensureHolders(it) }

        // The area is in Gradle's project order, by depth and then path, so each parent comes before its children.
        area.forEach { p ->  //TODO: If project.getProperty("cascade.enable")!=false then do this
            configureProject(p, buildTime)
        }
    }

    private fun ensureHolders(project: Project) {
        project.custom
        project.rawModule
        project.local
    }

    /**
     * Hides the parent's `local.properties` values from this project.
     * Gradle's property lookup reads through to parent projects, and an extra property can't be removed.
     * So for each key the parent set from its `local.properties`, and that this project doesn't have itself,
     * this project gets its own extra property with the value the parent saw before applying the file.
     * That value may be `null`.
     *
     * Only the direct parent is needed. Projects are configured parent first, so the parent already holds
     * the correct value for its own parent's local keys.
     */
    private fun shadowParentLocalKeys(project: Project) {
        val parent = project.parent?.takeIf { it.isCascadeConfigured } ?: return
        val extra = project.extensions.extraProperties
        parent.module.localPrevious.forEach { (key, previous) ->
            if (!extra.has(key)) {
                extra.set(key, previous)
                project.logger.debug("[${project.name}]:> Shadowed parent's local property $key with $previous")
            }
        }
    }

    private fun configureProject(project: Project, buildTime: BuildTime) {
        // Mark first: from here on, this project counts as inside the area, also for its children.
        project.module.configured = true

        buildTime.populate { key, value ->
            project.custom[key] = value
        }

        project.module.setProperty("default.project.group", "com.example")
        project.module.setProperty("default.project.version", buildTime.sanitizedBuildTime)

        project.inheritPropertiesFromFile("custom.properties",{it.custom},{it.custom})
        project.inheritPropertiesFromFile("module.properties",{it.rawModule},{it.custom})

        // "gradle.properties" files are not read here; Gradle has already loaded them as project properties.
        // Their values are used by placeholder resolution and may be overridden by the files below.

        // Keep the parent's local.properties values local to the parent. Must run before anything on this
        // project reads properties, including placeholder resolution.
        shadowParentLocalKeys(project)

        val localProps = project.loadProperties("local.properties")
        localProps.keys.forEach { key ->
            val keyStr = key.toString()
            project.module.localPrevious[keyStr] = project.findProperty(keyStr)
        }

        if (project.logger.isDebugEnabled) {
            project.logger.debug("[${project.name}]:> Project properties: ${project.providers.gradlePropertiesPrefixedBy("").get().filter { it.key != "parent" && it.key != "rootProject" }}")  // ✅ Fixed: project.logger
        }

        val allProps = Properties().apply {
            putAll(project.custom)
            putAll(localProps)
        }

        val resolvedProps = allProps.resolvePlaceholders(project, allProps)

        resolvedProps.forEach { (key, value) ->
            val keyStr = key.toString()
            if (project.hasProperty(keyStr)) {
                project.overrideProperty(keyStr, value)
                project.logger.debug("[${project.name}]:> Set existing property $keyStr to $value")
            } else {
                project.logger.debug("[${project.name}]:> Skipped setting $keyStr - not a known project property")
            }
            project.module.setProperty(keyStr, value)
        }

        project.logger.debug("[{}]:> All resolved properties: {}", project.name, resolvedProps)

        project.logger.debug("[${project.name}]:> Module properties: ${project.module.properties}")
        project.logger.debug("[${project.name}]:> Module property key types: ${project.module.properties.keys.map { it::class }}")

        val keyPrefix = "project."
        project.module.properties.forEach { (key, value) ->
            if (key.startsWith(keyPrefix)) {
                val name = key.removePrefix(keyPrefix)
                project.setProperty(name, value)
  //              project.extensions.extraProperties.set(name, value)
            } else {
                // Make every other key readable as a plain Gradle project property, e.g. with
                // project.findProperty(key), so conventions work with and without this plugin.
                // Not visible to providers.gradleProperty(key).
                project.extensions.extraProperties.set(key, value)
            }
        }

//project.logger.error("[${project.name}]:> Properties: ${project.properties}")

/* TO LOG ALL PROPERTIES, CLUMSY WAY:
        project.properties.forEach { (key,value) ->
            project.logger.error("    $key = $value")
        }
*/
    }
}
