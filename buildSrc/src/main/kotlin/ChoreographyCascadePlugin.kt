import com.yelstream.topp.grasp.cascade.plugin.BuildTime
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

object ProjectBuildTime {
    val instance: BuildTime by lazy {
//        println("🌍 Global build time computed ONCE: ${ZonedDateTime.now()}")
        BuildTime.now()
    }
}

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

fun Project.computeStructuralProperties(): Properties {
    val result = Properties()
    var currentDir: File? = projectDir.absoluteFile

    while (currentDir != null) {
        val currentDirKey = currentDir.absoluteFile
        val cachedProps = structuralPropertiesCache[currentDirKey]
        val dirProps = cachedProps ?: loadProperties(File(currentDir, "gradle.properties"))
        result.putAll(dirProps)
        structuralPropertiesCache[currentDirKey] = dirProps
        currentDir = if (currentDir.absolutePath == rootDir.absolutePath) null else currentDir.parentFile
    }
    return result
}

class ChoreographyCascadePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        initializeExtensions(project)
        project.subprojects.forEach { sub ->
            initializeExtensions(sub)
        }

        configureProject(project)
        project.subprojects.forEach { sub ->
            configureProject(sub)
        }
    }

    private fun initializeExtensions(project: Project) {
        project.extensions.extraProperties["custom"] = Properties()
        project.extensions.extraProperties["rawModule"] = Properties()
        project.extensions.extraProperties["nonRootGradle"] = Properties()
        project.extensions.extraProperties["structural"] = Properties()
        project.extensions.extraProperties["local"] = Properties()
    }

    private fun configureProject(project: Project) {
        project.extra["custom"] = Properties()
        project.extra["rawModule"] = Properties()
        project.extra["nonRootGradle"] = Properties()
        project.extra["structural"] = Properties()
        project.extra["local"] = Properties()

        ProjectBuildTime.instance.populate { key, value ->
            project.custom[key] = value
        }

        project.module.setProperty("default.project.group", "com.example")
        project.module.setProperty("default.project.version", ProjectBuildTime.instance.sanitizedBuildTime)



        val customProps = project.loadProperties("custom.properties")
        project.parent?.let { parent ->
            project.custom.putAll(parent.custom)
        }
        project.custom.putAll(customProps)



        val moduleProps = project.loadProperties("module.properties")
        project.parent?.let { parent ->
            project.rawModule.putAll(parent.custom)
        }
        project.rawModule.putAll(moduleProps)



        if (project != project.rootProject) {
            val nonRootProps = project.loadProperties("gradle.properties")
            project.parent?.let { parent ->
                project.nonRootGradle.putAll(parent.nonRootGradle)
            }
            project.nonRootGradle.putAll(nonRootProps)
            nonRootProps.forEach { (key, value) ->
                project.setProperty(key.toString(), value)
            }
        }



        if (project != project.rootProject) {
            val structuralProps = project.computeStructuralProperties()
            project.structural.putAll(structuralProps)
            structuralProps.forEach { (key, value) ->
                project.setProperty(key.toString(), value)
            }
        }

        val localProps = project.loadProperties("local.properties")
        localProps.forEach { (key, value) ->
            project.setProperty(key.toString(), value)
        }

        project.logger.debug("[${project.name}]:> Project properties: ${project.properties.filter { it.key != "parent" && it.key != "rootProject" }}")  // ✅ Fixed: project.logger

        val allProps = Properties().apply {
            putAll(project.custom)
            putAll(project.nonRootGradle)
            putAll(project.structural)
            putAll(project.loadProperties("local.properties")) // Re-load to ensure latest
        }

        val resolvedProps = allProps.resolvePlaceholders(project, allProps)

        resolvedProps.forEach { (key, value) ->
            val keyStr = key.toString()
            if (project.hasProperty(keyStr)) {
                project.setProperty(keyStr, value)
                project.logger.debug("[${project.name}]:> Set existing property $keyStr to $value")
            } else {
                project.logger.debug("[${project.name}]:> Skipped setting $keyStr - not a known project property")
                project.module.setProperty(keyStr, value)
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
            }
        }

//project.logger.error("[${project.name}]:> Properties: ${project.properties}")
    }
}

/*
import org.gradle.api.Plugin
import org.gradle.api.Project

class ChoreographyCascadePlugin : Plugin<Project> {
    override fun apply(project: Project) {
//        project.logger.info("ChoreographyCascadePlugin applied to ${project.name}")
        project.logger.error("ChoreographyCascadePlugin applied to ${project.name}")
    }
}
*/
