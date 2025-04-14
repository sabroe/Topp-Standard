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

package com.yelstream.topp.grasp

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.*

open class Module(private val project: Project) : ExtensionAware {
    private val properties = mutableMapOf<String, Any?>()

    override fun getExtensions() = project.extensions

    fun setProperty(name: String, value: Any?) {
        properties[name] = value
    }

    fun getProperty(name: String): Any? {
        return properties[name] ?: throw IllegalArgumentException("Property '$name' not found in module")
    }

    fun findProperty(name: String): Any? {
        return properties[name]
    }

    fun properties(configure: MutableMap<String, Any?>.() -> Unit) {
        properties.configure()
    }

    var description: String? = null

    val extra: ExtraPropertiesExtension
        get() = project.extensions.extraProperties
}

open class SubModule(private val project: Project) {
    var nestedProp: String? = null
}

class ModulePlugin : org.gradle.api.Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create<Module>("module", project)
        project.extensions.getByType<Module>().extensions.create<SubModule>("subModule", project)
    }
}
