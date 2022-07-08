plugins {
    id("maven-publish")
    id("java-gradle-plugin")
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
    plugins {
        group = "io.github.vichid"
        version = System.getenv("GITHUB_REF_NAME")
        register("root") {
            id = "io.github.vichid.root"
            implementationClass = "AndroidProjectConventionPlugin"
        }
        register("application") {
            id = "io.github.vichid.application"
            implementationClass = "ApplicationConventionPlugin"
        }
        register("applicationCompose") {
            id = "io.github.vichid.application.compose"
            implementationClass = "ApplicationComposeConventionPlugin"
        }
        register("library") {
            id = "io.github.vichid.library"
            implementationClass = "LibraryConventionPlugin"
        }
        register("libraryCompose") {
            id = "io.github.vichid.library.compose"
            implementationClass = "LibraryComposeConventionPlugin"
        }
        register("jvm") {
            id = "io.github.vichid.jvm"
            implementationClass = "JvmConventionPlugin"
        }
        register("test") {
            id = "io.github.vichid.test"
            implementationClass = "TestConventionPlugin"
        }
        register("kotlin") {
            id = "io.github.vichid.kotlin"
            implementationClass = "KotlinConventionPlugin"
        }
        register("module") {
            id = "io.github.vichid.module"
            implementationClass = "ModuleConventionPlugin"
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
