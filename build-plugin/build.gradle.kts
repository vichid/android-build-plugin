plugins {
    `maven-publish`
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/vichid/android-build-plugin")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN_PUBLISH")
            }
        }
    }
}

gradlePlugin {
    group = "io.github.vichid"
    version = System.getenv("GITHUB_REF_NAME")
    plugins {
        register("root") {
            id = "io.github.vichid.root"
            implementationClass = "io.github.vichid.plugins.AndroidProjectConventionPlugin"
        }
        register("application") {
            id = "io.github.vichid.application"
            implementationClass = "io.github.vichid.plugins.ApplicationConventionPlugin"
        }
        register("applicationCompose") {
            id = "io.github.vichid.application.compose"
            implementationClass = "io.github.vichid.plugins.ApplicationComposeConventionPlugin"
        }
        register("library") {
            id = "io.github.vichid.library"
            implementationClass = "io.github.vichid.plugins.LibraryConventionPlugin"
        }
        register("libraryCompose") {
            id = "io.github.vichid.library.compose"
            implementationClass = "io.github.vichid.plugins.LibraryComposeConventionPlugin"
        }
        register("jvm") {
            id = "io.github.vichid.jvm"
            implementationClass = "io.github.vichid.plugins.JvmConventionPlugin"
        }
        register("test") {
            id = "io.github.vichid.test"
            implementationClass = "io.github.vichid.plugins.TestConventionPlugin"
        }
        register("kotlin") {
            id = "io.github.vichid.kotlin"
            implementationClass = "io.github.vichid.plugins.KotlinConventionPlugin"
        }
        register("module") {
            id = "io.github.vichid.module"
            implementationClass = "io.github.vichid.plugins.ModuleConventionPlugin"
        }
    }
}

dependencies {
    implementation(libs.gradlePlugins.dependencyAnalysis)
    implementation(libs.gradlePlugins.doctor)
    implementation(libs.gradlePlugins.moduleGraphAssertion)
    implementation(libs.gradlePlugins.ruler)
    implementation(libs.gradlePlugins.versionUpdate)
    implementation(libs.mixpanel.java)
    implementation(libs.moshi.kotlin)

    compileOnly(libs.detekt.api)
    compileOnly(libs.gradlePlugins.android)
    compileOnly(libs.gradlePlugins.anvil)
    compileOnly(libs.gradlePlugins.autoManifest)
    compileOnly(libs.gradlePlugins.detekt)
    compileOnly(libs.gradlePlugins.kotlin)
    compileOnly(libs.gradlePlugins.kover) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
    compileOnly(libs.gradlePlugins.ksp)
    compileOnly(libs.gradlePlugins.paparazzi)
}
