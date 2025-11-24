plugins {
//    id("java")
//    id("com.yelstream.topp.grasp.feature.spotbugs")
//    id("com.yelstream.topp.grasp.feature.sonarqube")
}

//subprojects {
    afterEvaluate {
        if (plugins.hasPlugin("java") || plugins.hasPlugin("java-library")) {
println("afterEvaluate!")
            apply(plugin = "com.yelstream.topp.grasp.feature.spotbugs")
            apply(plugin = "com.yelstream.topp.grasp.feature.sonarqube")
        }
    }
//}