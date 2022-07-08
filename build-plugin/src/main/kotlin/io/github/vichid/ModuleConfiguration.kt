package io.github.vichid

import org.jetbrains.kotlin.cli.common.repl.replAddLineBreak
import java.util.Locale

enum class ModuleConfiguration {
    API,
    IMPL,
    DEMO,
    FAKES;

    override fun toString(): String = name.toLowerCase(Locale.getDefault())
}

fun StringBuilder.appendConfiguration(moduleConfiguration: ModuleConfiguration): StringBuilder =
    when (moduleConfiguration) {
        ModuleConfiguration.API -> appendJvmPlugin()
        ModuleConfiguration.IMPL -> appendAndroidLibraryPlugin()
            .appendLibraryDependency()
        ModuleConfiguration.DEMO -> appendApplication()
        ModuleConfiguration.FAKES -> appendAndroidLibraryPlugin()
            .appendLibraryDependency()
    }

private fun StringBuilder.appendJvmPlugin(): StringBuilder = append(
    """
    plugins {
        id("io.github.vichid.jvm")
    }
"""
        .trimIndent()
        .replAddLineBreak()
)

private fun StringBuilder.appendAndroidLibraryPlugin(): StringBuilder = append(
    """
    plugins {
        id("io.github.vichid.library")
        id("com.squareup.anvil")
    }
"""
        .trimIndent()
        .replAddLineBreak()
)

private fun StringBuilder.appendApplication(): StringBuilder = append(
    """
    plugins {
        id("io.github.vichid.application.compose")
        id("com.squareup.anvil")
    }

    android {
        defaultConfig {
            applicationId = "com.example.playground.&s"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
""".trimIndent()
)

private fun StringBuilder.appendLibraryDependency(): StringBuilder = append(
    """
    dependencies {
        api(projects.&s.api)
    }
"""
        .trimIndent()
        .replAddLineBreak()
)
