plugins {
    `kotlin-dsl`
    `java-library`
    `groovy-gradle-plugin`
    `java-gradle-plugin`
    // `maven-publish`
}

group = "com.example"
version = "1.0.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
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
        create("featureRoot") {
            id = "com.example.feature-root"
            implementationClass = "com.example.feature.FeatureRootPlugin"
            displayName = "Feature Root Plugin"
            description = "Aggregates feature plugins for Topp-Standard project"
        }
        create("dependencyManagement") {
            id = "com.example.dependency-management"
            implementationClass = "com.example.depman.DependencyManagementPlugin"
            displayName = "Dependency Management Plugin"
            description = "Manages default versions and BOMs for Topp-Standard project"
        }
        create("lombokFeature") {
            id = "com.yelstream.topp.grasp.feature.lombok"
            implementationClass = "com.yelstream.topp.grasp.feature.lombok.LombokFeaturePlugin"
            displayName = "Yelstream Topp Grasp Lombok Feature Plugin"
            description = "Yelstream Topp Grasp Lombok Feature Plugin does xxx"
        }
        create("slf4jFeature") {
            id = "com.yelstream.topp.grasp.feature.slf4j"
            implementationClass = "com.yelstream.topp.grasp.feature.slf4j.SLF4JFeaturePlugin"
            displayName = "Yelstream Topp Grasp SLF4J Feature Plugin"
            description = "Yelstream Topp Grasp SLF4J Feature Plugin does xxx"
        }

        create("choreographyCascade") {
            id = "com.yelstream.topp.grasp.choreography-cascade"
            implementationClass = "com.yelstream.topp.grasp.cascade.plugin.ChoreographyCascadePlugin"
            displayName = "Yelstream Topp Grasp 'Choreography Cascade' Plugin"
            description = "Orchestrates hierarchical property inheritance with local override choreography."
        }

        create("versionResolution") {
            id = "com.yelstream.topp.grasp.version-resolution"
            implementationClass = "com.yelstream.topp.grasp.resolve.plugin.DynamicVersionResolverPlugin"
            displayName = "Yelstream Topp Grasp 'Version Resolution' Plugin"
            description = "Resolves module versions in case no other mechanism is in effect."
        }
    }
}

/*
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("Topp-Standard Plugins")
                description.set("Gradle plugins for feature and dependency management in Topp-Standard")
                url.set("https://github.com/sabroe/Topp-Grasp")
                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
            }
        }
    }
    repositories {
        mavenLocal() // Publish to local Maven repository for testing
        // Add other repositories (e.g., Nexus, Maven Central) as needed
    }
}
*/

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.20")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.3.0-Beta1")
    implementation("org.tomlj:tomlj:1.1.1")
}
