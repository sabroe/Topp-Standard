/*
 * Project: Topp Grasp
 * GitHub: https://github.com/sabroe/Topp-Grasp
 *
 * Copyright 2022-2025 Morten Sabroe Mortensen
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
 *
    module.properties
    private.properties
    structure.properties

    Logical Hierarchy
    Physical Hierarchy
 */

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Properties

val buildZonedTime: ZonedDateTime = ZonedDateTime.now()
val buildTimeZulu: String = DateTimeFormatter.ISO_INSTANT.format(buildZonedTime) // E.g., '2011-12-03T10:15:30Z'
val buildTimeOffset: String = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(buildZonedTime) // E.g., '2011-12-03T10:15:30+01:00'
val buildTimeZoned: String = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(buildZonedTime) // E.g., '2011-12-03T10:15:30+01:00[Europe/Copenhagen]'
val buildTime: String = buildTimeZulu
val sanitizedBuildTime: String = buildTime.replace("T", "-").replace(":", "") // E.g., '2025-04-02-174530Z'

// Define extension properties at the top level for type safety (optional)
val Project.custom: Properties
    get() = extra["custom"] as? Properties ?: Properties().also { extra["custom"] = it }
val Project.nonRootGradle: Properties
    get() = extra["nonRootGradle"] as? Properties ?: Properties().also { extra["nonRootGradle"] = it }

fun Project.loadProperties(fileName: String): Properties {
    val properties = Properties()
    val file = file(fileName)
    logger.info("[${name}]:> Looking for properties file: $file")
    if (file.exists()) {
        file.reader().use { properties.load(it) }
        logger.info("[${name}]:> Did find and read properties file: $file")
    }
    return properties
}

allprojects {
    extra["custom"] = Properties()
    extra["nonRootGradle"] = Properties()

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

    /*
     * Populate custom properties from parent custom properties and local module file "custom.properties".
     * All custom properties are inherited from the root project and down through all sub-modules.
     * Each custom property can be accessed as 'ext.custom.xxx' or just 'custom.xxx'.
     * Inherited custom properties is a main feature.
     */
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

    /*
     * Populate project properties from parent, non-root properties and local module file "gradle.properties.
     * This allows some opening to move root projects down to become sub-modules and transfer "gradle.properties"
     * with either their full or part content down to a sub-module and without too much hassle like e.g. moving the properties.
     * For now, this feature is a convenience more like an actually intended functionality.
     */
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

    /*
     * Populate project properties with additional properties from local module file "local.properties".
     * This allows for a forced override of project properties within a local sub-module.
     * Local, forced override of project properties is a main feature.
     */
    run {
        val fileName = "local.properties"
        val properties = loadProperties(fileName)

        properties.forEach { key, value ->
            setProperty(key.toString(), value)
        }

        logger.debug("[${name}]:> Project properties introduced by local module: $properties")
    }

    logger.debug("[${name}]:> Project properties: ${properties.filter { it.key != "parent" && it.key != "rootProject" }}")

    /*
     * Set project group and version, if not already set.
     * With fallback from first custom properties and otherwise fixed values.
     * This construction allows to set the project group and version just once in the custom properties file in
     * the root of the project while allowing for a local overwrite within a sub-module, if necessary.
     */
    run {
        group = custom["project.group"] ?: "com.example"
        version = custom["project.version"] ?: sanitizedBuildTime

        logger.info("GROUP:   $group")
        logger.info("VERSION: $version")
    }
}
