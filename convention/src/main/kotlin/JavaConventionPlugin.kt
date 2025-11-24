import org.gradle.kotlin.dsl.apply

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.kotlin.dsl.*
import java.io.InputStream
import java.net.URL

class JavaConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
//        project.apply(from = "java-convention.gradle.kts")

/*
        val scriptStream: URL? = this::class.java.getResource("java-convention.gradle.kts")
            project.apply(from = it.toUri().toString())
        } ?: throw IllegalArgumentException("Script not found in classpath: java-convention.gradle.kts")
*/

    }
}
