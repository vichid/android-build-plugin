rootProject.name = "build-logic"

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

include("build-plugin")
//if ("detekt.rules.path" in extra.properties) {
//    val path = (extra["detekt.rules.path"] as String)
//    includeBuild(path) {
//        dependencySubstitution {
//            substitute(module("io.github.vichid:detekt-rules"))
//                .using(project(":"))
//        }
//    }
//}

