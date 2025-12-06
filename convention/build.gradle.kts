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
