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
 * "Topp Standard" root project Gradle build.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-12-20
 */

buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("convention.root-project")
    id("convention.java-module") apply false

    id("convention.conservative-build") apply false
    id("convention.build-reapply") apply false

    id("com.yelstream.topp.grasp.feature.root")

    id("com.yelstream.topp.grasp.choreography-cascade")
    id("com.yelstream.topp.grasp.version-resolution") apply false
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

allprojects {
    apply(plugin = "convention.root-project")

    apply(plugin = "convention.conservative-build")
    apply(plugin = "convention.build-reapply")
}

subprojects {
    apply(plugin = "convention.java-module")

    apply(plugin = "com.yelstream.topp.grasp.feature.root")
    apply(plugin = "com.yelstream.topp.grasp.version-resolution")
}

/*
 Note:
    To get a page with report links, consider executing:
    $ find . -name "*.html" | grep -E "/build/(docs|reports)/[^.]+/[[:alnum:]]+[.]html"
*/


val featured = listOf(
    "Topp-Standard-Operation-Comparison-Library",
    "Topp-Standard-Operation-Reflection-Library",
    "Topp-Standard-Operation-Type-Library",
    "Topp-Standard-Annotation-Intention-Library",
    "Topp-Standard-Stream-Collection-Library",
    "Topp-Standard-Dual-Access-IO-Library",
    "Topp-Standard-System-Holder-Library",
    "Topp-Standard-Time-Library",
    "Topp-Standard-Time-Legacy-Library",
    "Topp-Standard-XML-Bind-Library",
    "Topp-Standard-XML-Process-Library",
    "Topp-Standard-XML-Stream-Library",
    "Topp-Standard-XML-Time-Library",
    "Topp-Standard-Core-Library",
    "Topp-Standard-Logging-SLF4J-Base-Library",
    "Topp-Standard-Logging-SLF4J-Proxy-Logger-Library",
    "Topp-Standard-Logging-SLF4J-Console-Logger-Library",
    "Topp-Standard-Logging-SLF4J-Service-Provider-Library"
)

tasks.register("generateModuleTable") {
    doLast {
        val baseGroup = "com.yelstream.topp.standard"
        val githubBase = "https://github.com/sabroe/Topp-Standard/tree/main/module"

        val projectsByName = rootProject.subprojects.associateBy { it.name }

        val rows = featured.map { name ->
            val project = projectsByName[name] ?: error("Project not found: $name")
            val gradlePath = project.path.removePrefix(":module")
            val moduleInfo = file("${project.projectDir}/src/main/java/module-info.java")

            val jpms = moduleInfo.readLines()
                .map { it.trim() }
                .firstOrNull { it.startsWith("module ") }
                ?.removePrefix("module ")
                ?.removeSuffix("{")
                ?.trim()
                ?: error("Missing module-info.java in ${project.path}")

            val artifactId = project.name
                .removePrefix("Topp-Standard-")
                .removeSuffix("-Library")
                .replace(Regex("([a-z])([A-Z])"), "$1-$2")
                .lowercase()

            val artifactIdKebab = "topp-standard-$artifactId"

            val githubPath = project.path
                .removePrefix(":module:")
                .replace(":", "/")

            val mavenUrl = "https://central.sonatype.com/artifact/$baseGroup/$artifactIdKebab"
            val javadocUrl = "https://javadoc.io/doc/$baseGroup/$artifactIdKebab"
            val githubUrl = "$githubBase/$githubPath"

            Triple(
                "[`$artifactIdKebab`]($mavenUrl)",
                "[`$jpms`]($javadocUrl)",
                "[`$gradlePath`]($githubUrl)"
            )
        }

        val col1 = rows.maxOf { it.first.length }
        val col2 = rows.maxOf { it.second.length }
        val col3 = rows.maxOf { it.third.length }

        fun pad(s: String, width: Int) = s + " ".repeat(width - s.length)

        println("| ${pad("Artifact @ Maven Central", col1)} " +
                "| ${pad("JPMS Module @ JavaDoc", col2)} " +
                "| ${pad("Gradle Module @ GitHub", col3)} |"
        )
        println("|-${"-".repeat(col1)}-" +
                "|-${"-".repeat(col2)}-" +
                "|-${"-".repeat(col3)}-|")
        rows.forEach {
            println("| ${pad(it.first, col1)} " +
                    "| ${pad(it.second, col2)} " +
                    "| ${pad(it.third, col3)} |")
        }
    }
}
