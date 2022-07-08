package io.github.vichid

import org.gradle.api.Project
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

object KspConfiguration {
    fun Project.configureKsp(): (AppliedPlugin).() -> Unit =
        {
            extensions.configure<KotlinAndroidProjectExtension> {
                sourceSets.getByName("debug") {
                    kotlin.srcDir("build/generated/ksp/debug/kotlin")
                }
                sourceSets.getByName("main") {
                    kotlin.srcDir("build/generated/ksp/main/kotlin")
                }
                sourceSets.getByName("test") {
                    kotlin.srcDir("build/generated/ksp/test/kotlin")
                }
            }
        }
}
