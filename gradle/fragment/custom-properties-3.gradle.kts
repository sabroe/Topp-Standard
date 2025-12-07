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

/*
 * In Gradle’s normal semantics, submodules inherit properties (like those in gradle.properties or set via -P) only
 * from the root project, regardless of their position in the module hierarchy.
 * There’s no automatic inheritance from intermediate parent modules—only the root’s properties propagate down to all subprojects.
 */

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Properties
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.*

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

val buildZonedTime: ZonedDateTime = ZonedDateTime.now()
val buildTimeZulu: String = DateTimeFormatter.ISO_INSTANT.format(buildZonedTime) // E.g., '2011-12-03T10:15:30Z'
val buildTimeOffset: String = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(buildZonedTime) // E.g., '2011-12-03T10:15:30+01:00'
val buildTimeZoned: String = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(buildZonedTime) // E.g., '2011-12-03T10:15:30+01:00[Europe/Copenhagen]'
val buildTime: String = buildTimeZulu
val sanitizedBuildTime: String = buildTime.replace("T", "-").replace(":", "") // E.g., '2025-04-02-174530Z'

val Project.rawModule: Properties
    get() = extra["rawModule"] as? Properties ?: Properties().also { extra["rawModule"] = it }

val Project.custom: Properties
    get() = extra["custom"] as? Properties ?: Properties().also { extra["custom"] = it }

val Project.nonRootGradle: Properties
    get() = extra["nonRootGradle"] as? Properties ?: Properties().also { extra["nonRootGradle"] = it }

val Project.structural: Properties
    get() = extra["structural"] as? Properties ?: Properties().also { extra["structural"] = it }

val Project.local: Properties
    get() = extra["local"] as? Properties ?: Properties().also { extra["local"] = it }

// Cache for structural properties: Map<absolute-directory-as-File, Properties>
val structuralPropertiesCache = mutableMapOf<File, Properties>()

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

fun Properties.resolvePlaceholders(project: Project, sources: Properties): Properties {
    val resolved = Properties()
    val visited = mutableSetOf<String>() // Track keys to prevent cycles

    fun resolveValue(value: String, keyChain: Set<String> = emptySet()): String {
        if (!value.contains("\${") || keyChain.contains(value)) return value
        return value.replace(Regex("\\$\\{([^}]+)\\}")) { match ->
            val propKey = match.groupValues[1]
            if (propKey in keyChain) return@replace match.value // Avoid cycles
            val resolvedValue = sources.getProperty(propKey)
                ?: sources.getProperty("default.$propKey") // Fallback to default
                ?: project.findProperty(propKey)?.toString()
                ?: project.findProperty("default.$propKey")?.toString()
                ?: System.getProperty(propKey)
                ?: System.getProperty("default.$propKey")
                ?: match.value
            resolveValue(resolvedValue, keyChain + propKey) // Recurse on resolved value
        }
    }

    forEach { key, value ->
        resolved[key] = resolveValue(value.toString(), setOf(key.toString()))
    }
    return resolved
}

// Compute structural properties by walking up the file system
fun Project.computeStructuralProperties(): Properties {
    val result = Properties()
    var currentDir: File? = projectDir.absoluteFile

    while (currentDir != null) {
        val currentDirKey = currentDir.absoluteFile

        val cachedProps = structuralPropertiesCache[currentDirKey]

        val dirProps =
            if (cachedProps != null) {
                cachedProps
            } else {
                val gradlePropsFile = File(currentDir, "gradle.properties")
                loadProperties(gradlePropsFile)
            }

        result.putAll(dirProps)

        structuralPropertiesCache[currentDirKey] = dirProps

        currentDir =
            if (currentDir.absolutePath == rootDir.absolutePath) {
                null
            } else {
                currentDir.parentFile
            }
    }

    return result
}

allprojects {
    extra["custom"] = Properties()
    extra["rawModule"] = Properties()
    extra["nonRootGradle"] = Properties()
    extra["structural"] = Properties()
    extra["local"] = Properties()

    /*
     * Set all default custom properties.
     * Allows for later overwrite.
     */
    run {
        custom["build-time"] = buildTime
        custom["build-time-zulu"] = buildTimeZulu
        custom["build-time-offset"] = buildTimeOffset
        custom["build-time-zoned"] = buildTimeZoned
    }

    run {
        project.module.setProperty("default.project.group","com.example")
        project.module.setProperty("default.project.version",sanitizedBuildTime)
    }

    /*
     * Populate custom properties from parent custom properties and local module file "custom.properties".
     * All custom properties are inherited from the root project and down through all sub-modules.
     * Each custom property can be accessed as 'ext.custom.xxx' or just 'custom.xxx'.
     * Inherited custom properties is a main feature.
     */
/*
    run {
        val fileName = "custom.properties"
        val properties = loadProperties(fileName)

        if (parent != null) {
            custom.putAll(parent!!.custom)
        }
        custom.putAll(properties)

        logger.debug("[${name}]:> Custom properties introduced by parent module: ${parent?.custom}")
        logger.debug("[${name}]:> Custom properties introduced by local module: $properties")
        logger.debug("[${name}]:> Custom properties resolved: $custom")
    }
*/
    run {
        val properties = loadProperties("custom.properties")
        if (parent != null) custom.putAll(parent!!.custom)
        custom.putAll(properties)
    }

    run {
        val properties = loadProperties("module.properties")
        if (parent != null) rawModule.putAll(parent!!.custom)
        rawModule.putAll(properties)
    }

    /*
     * Populate project properties from parent, non-root properties and local module file "gradle.properties.
     * This allows some opening to move root projects down to become sub-modules and transfer "gradle.properties"
     * with either their full or part content down to a sub-module and without too much hassle like e.g. moving the properties.
     * For now, this feature is a convenience more like an actually intended functionality.
     */
/*
    run {
        if (this != rootProject) { // Only for submodules
            val fileName = "gradle.properties"
            val properties = loadProperties(fileName)

            if (parent != null) {
                nonRootGradle.putAll(parent!!.nonRootGradle)
            }
            nonRootGradle.putAll(properties)

            nonRootGradle.forEach { (key, value) ->
                setProperty(key.toString(), value)
            }

            logger.debug("[${name}]:> Non-root \"Gradle\" properties introduced by parent module: ${parent?.nonRootGradle}")
            logger.debug("[${name}]:> Non-root \"Gradle\" properties introduced by local module: $properties")
            logger.debug("[${name}]:> Non-root \"Gradle\" properties resolved: $nonRootGradle")
        }
    }
*/
    run {
        if (this != rootProject) {
            val properties = loadProperties("gradle.properties")
            if (parent != null) nonRootGradle.putAll(parent!!.nonRootGradle)
            nonRootGradle.putAll(properties)
            properties.forEach { (key, value) -> setProperty(key.toString(), value) }
        }
    }

    /*
     * Populate project properties from the structural hierarchy (file system) using gradle.properties files.
     * This reads properties from the current directory and all parent directories up to the root,
     * caching them for efficiency, and applies them as project properties for submodules.
     */
/*
    run {
        if (this != rootProject) {
            val properties = computeStructuralProperties()

            structural.putAll(properties)

            properties.forEach { (key, value) ->
                setProperty(key.toString(), value)
            }

            logger.debug("[${name}]:> Structual properties resolved from file system: $properties")
        }
    }
 */
    run {
        if (this != rootProject) {
            val properties = computeStructuralProperties()
            structural.putAll(properties)
            properties.forEach { (key, value) -> setProperty(key.toString(), value) }
        }
    }

    /*
     * Populate project properties with additional properties from local module file "local.properties".
     * This allows for a forced override of project properties within a local sub-module.
     * Local, forced override of project properties is a main feature.
     */
/*
    run {
        val fileName = "local.properties"
        val properties = loadProperties(fileName)

        properties.forEach { key, value ->
            setProperty(key.toString(), value)
        }

        logger.debug("[${name}]:> Project properties introduced by local module: $properties")
    }
 */
    run {
        val properties = loadProperties("local.properties")
        properties.forEach { key, value -> setProperty(key.toString(), value) }
    }

    logger.debug("[${name}]:> Project properties: ${properties.filter { it.key != "parent" && it.key != "rootProject" }}")

    // Combine all sources into one Properties object
    run {
        val allProps = Properties().apply {
            putAll(custom)
            putAll(nonRootGradle)
            putAll(structural)
            putAll(loadProperties("local.properties")) // Re-load to ensure latest
        }

        // Resolve placeholders using only allProps
        val resolvedProps = allProps.resolvePlaceholders(project,allProps)

        resolvedProps.forEach { key, value ->
            val keyStr = key.toString()
            if (project.hasProperty(keyStr)) {
                setProperty(keyStr, value)
                logger.debug("[${name}]:> Set existing property $keyStr to $value")
            } else {
                logger.debug("[${name}]:> Skipped setting $keyStr - not a known project property")
                project.module.setProperty(keyStr,value)
            }
            project.module.setProperty(keyStr,value)
        }

        logger.debug("[${name}]:> All resolved properties: $resolvedProps")
    }

    logger.debug("[${name}]:> Module properties: ${project.module.properties}")
    logger.debug("[${name}]:> Module property key types: ${project.module.properties.keys.map { it::class }}")

    /*
     * Set all module properties of the form "project.x.y.z" as project properties "x.y.z".
     * There is no check, nor any restriction, as to which if any property is viable as a project property.
     */
    run {
        val keyPrefix="project."
        project.module.properties.forEach { (key, value) ->
            if (key is String && key.startsWith(keyPrefix)) {
                val name = key.removePrefix(keyPrefix)
                project.setProperty(name, value)
            }
        }
    }
}
