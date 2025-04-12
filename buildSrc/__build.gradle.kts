plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // Safe version, Kotlin supports up to 22
    }
}

gradlePlugin {
    plugins {
        create("modulePlugin") {
            id = "com.yelstream.topp.grasp.module"
            implementationClass = "com.yelstream.topp.grasp.ModulePlugin"
        }
    }
}
