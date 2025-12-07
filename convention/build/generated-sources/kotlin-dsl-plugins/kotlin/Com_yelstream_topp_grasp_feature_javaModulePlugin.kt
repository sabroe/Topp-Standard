/**
 * Precompiled [com.yelstream.topp.grasp.feature.java-module.gradle.kts][Com_yelstream_topp_grasp_feature_java_module_gradle] script plugin.
 *
 * @see Com_yelstream_topp_grasp_feature_java_module_gradle
 */
public
class Com_yelstream_topp_grasp_feature_javaModulePlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Com_yelstream_topp_grasp_feature_java_module_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
