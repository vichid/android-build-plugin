rootProject.name = "build-logic"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

include("build-plugin")

if ("detekt.rules.path" in extra.properties) {
    val path = (extra["detekt.rules.path"] as String)
    includeBuild(path) {
        dependencySubstitution {
            substitute(module("io.github.vichid:detekt-rules"))
                .using(project(":"))
        }
    }
}
