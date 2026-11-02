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
     * The keys of this project's own `local.properties`. Child projects hide these keys (see [hiddenKeys]).
     */
    val localKeys = mutableSetOf<String>()

    /**
     * The keys hidden on this project: `local.properties` keys of the projects above it in the area,
     * which this project doesn't define itself. Each one is set as an extra property with the value `null`.
     */
    val hiddenKeys = mutableSetOf<String>()

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
 * True if this project has the property itself: a property of the project object, such as `version`,
 * or one of its own extra properties, for example from a `gradle.properties` file.
 * Unlike [Project.hasProperty], it doesn't look in parent projects. Gradle 9.8 deprecates that lookup,
 * and Gradle 10 removes it.
 */
fun Project.hasOwnProperty(name: String): Boolean =
    extensions.extraProperties.has(name) ||
        org.codehaus.groovy.runtime.InvokerHelper.getMetaClass(this).hasProperty(this, name) != null

/**
 * Gives the value of a property this project has itself, see [hasOwnProperty], or `null`.
 * Unlike [Project.findProperty], it doesn't look in parent projects.
 */
fun Project.findOwnProperty(name: String): Any? {
    val extra = extensions.extraProperties
    if (extra.has(name)) return extra[name]
    return org.codehaus.groovy.runtime.InvokerHelper.getMetaClass(this).hasProperty(this, name)?.getProperty(this)
}

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
                ?: project.findOwnProperty(propKey)?.toString()
                ?: project.findOwnProperty("default.$propKey")?.toString()
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
     * Hides the `local.properties` keys of the projects above this one from this project.
     * The plugin never copies them down, but Gradle 9 still finds a parent's extra property through its
     * deprecated lookup in parent projects. Gradle 10 removes that lookup, and this step is then not needed.
     *
     * Every such key that this project doesn't have itself is set as an extra property with the value `null`,
     * which is what Gradle 10 gives. The parent's hidden keys are passed on as well, so no project below
     * has to look in its parent either.
     */
    private fun hideParentLocalKeys(project: Project) {
        val parent = project.parent?.takeIf { it.isCascadeConfigured } ?: return
        (parent.module.localKeys + parent.module.hiddenKeys).forEach { key ->
            if (!project.hasOwnProperty(key)) {
                project.extensions.extraProperties.set(key, null)
                project.module.hiddenKeys += key
                project.logger.debug("[${project.name}]:> Hid local property $key of a project above")
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

        val localProps = project.loadProperties("local.properties")
        localProps.keys.forEach { key -> project.module.localKeys += key.toString() }

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
            if (project.hasOwnProperty(keyStr)) {
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

        // Last, so that every key this project defines itself is already set and is not hidden.
        hideParentLocalKeys(project)

//project.logger.error("[${project.name}]:> Properties: ${project.properties}")

/* TO LOG ALL PROPERTIES, CLUMSY WAY:
        project.properties.forEach { (key,value) ->
            project.logger.error("    $key = $value")
        }
*/
    }
}
