import org.gradle.kotlin.dsl.apply

plugins {
}

afterEvaluate {
    if (plugins.hasPlugin("java") || plugins.hasPlugin("java-library")) {
        println("afterEvaluate!")
        apply(plugin = "com.yelstream.topp.grasp.feature.spotbugs")
        apply(plugin = "com.yelstream.topp.grasp.feature.sonarqube")
    }
}