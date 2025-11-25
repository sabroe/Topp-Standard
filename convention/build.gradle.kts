plugins {
    `kotlin-dsl`
    `groovy-gradle-plugin`
    `java-gradle-plugin`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

gradlePlugin {
    plugins {
/*
        register("javaConvention") {
            id = "com.yelstream.topp.grasp.convention.java"
            implementationClass = "JavaConventionPlugin"
        }
*/
/*
        create("graspJava") {
            id = "com.yelstream.topp.grasp.convention.java"
            implementationClass = "JavaConventionPlugin"
        }
*/
    }
}

dependencies {
    implementation("com.github.spotbugs.snom:spotbugs-gradle-plugin:6.2.5")
//    implementation("com.github.spotbugs:spotbugs:4.9.8")
    implementation("org.sonarqube:org.sonarqube.gradle.plugin:7.1.0.6387")
    implementation("com.dorongold.task-tree:com.dorongold.task-tree.gradle.plugin:4.0.1")
}
