/*
 * Project: Topp Grasp
 * GitHub: https://github.com/sabroe/Topp-Grasp
 *
 * Copyright 2022-2026 Morten Sabroe Mortensen
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

import me.champeau.gradle.igp.gitRepositories

plugins {
    id("me.champeau.includegit")
}

val sP = java.util.Properties().apply {
    val propsFile = file("settings.properties")
    if (propsFile.exists()) {
        propsFile.inputStream().use { load(it) }
    } else {
        logger.warn("settings.properties not found – using defaults")
    }
}

gitRepositories {
    useGitCli = sP.getProperty("boot.git.use.cli", "true").toBoolean()
    checkoutsDirectory.set(file("${System.getProperty("user.home")}/.topp/gradle/script/"))
    include("Topp-Gradle-Convention") {
        uri.set(sP.getProperty("boot.git.repo.uri","https://github.com/sabroe/Topp-Gradle-Convention.git"))
        branch.set(sP.getProperty("boot.git.repo.branch", "main"))
        // includeBuild(".")
    }
}
