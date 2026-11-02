# Not used

This folder is **not** part of the build. `includeBuild("convention")` is commented out in the root
`settings.gradle.kts`, so nothing here is compiled or applied, and changes here have no effect.

The conventions the build uses come from the `Topp-Gradle-Convention` repository
(https://github.com/sabroe/Topp-Gradle-Convention), branch `main`. The `bootstrap` settings plugin checks it out to
`~/.topp/gradle/script/Topp-Gradle-Convention/` and includes `module/Topp-Gradle-Feature` from there.
Make changes to the conventions in that repository.

The files here are an older copy and differ from the ones in use.
