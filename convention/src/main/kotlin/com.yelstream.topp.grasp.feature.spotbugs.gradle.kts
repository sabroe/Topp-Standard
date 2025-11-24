plugins {
    id("com.github.spotbugs-base")  //Reference: https://plugins.gradle.org/plugin/com.github.spotbugs-base
}

spotbugs {
    ignoreFailures = false
    showStackTraces = true
    showProgress = true

//    effort.set(com.github.spotbugs.snom.effort.Effort.MAX)
}

dependencies {
    spotbugs("com.github.spotbugs:spotbugs:4.9.8")
}
