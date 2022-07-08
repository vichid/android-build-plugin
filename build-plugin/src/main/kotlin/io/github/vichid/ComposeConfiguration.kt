package io.github.vichid

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object ComposeConfiguration {

    fun Project.configureComposeApp(): (AppliedPlugin).() -> Unit =
        {
            configureCompose(extensions.getByType<BaseAppModuleExtension>())
        }

    fun Project.configureComposeLibrary(): (AppliedPlugin).() -> Unit =
        {
            configureCompose(extensions.getByType<LibraryExtension>())
        }

    @Suppress("UnstableApiUsage")
    private fun Project.configureCompose(commonExtension: CommonExtension<*, *, *, *>) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        with(commonExtension) {
            buildFeatures {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion =
                    libs.findVersion("compose.compiler").get().toString()
            }
        }
        if (findProperty("playground.enableComposeCompilerReports") == "true") {
            val composeTxtReportMerge by tasks.registering(ComposeReportMergeTask::class) {
                output.set(rootProject.buildDir.resolve("reports/compose-metrics/merged-composables.txt"))
            }
            val composeCsvReportMerge by tasks.registering(ComposeReportMergeTask::class) {
                output.set(rootProject.buildDir.resolve("reports/compose-metrics/merged-composables.csv"))
            }
            composeTxtReportMerge.configure {
                input.from("${buildDir.absolutePath}/compose-metrics/${project.name}_release-composables.txt")
            }
            composeCsvReportMerge.configure {
                input.from("${buildDir.absolutePath}/compose-metrics/${project.name}_release-composables.csv")
            }
            tasks.withType<KotlinCompile>().configureEach {
                finalizedBy(composeTxtReportMerge)
                finalizedBy(composeCsvReportMerge)
                kotlinOptions {
                    freeCompilerArgs = freeCompilerArgs +
                        listOf(
                            "-P",
                            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                                buildDir.absolutePath + "/compose-metrics"
                        ) +
                        listOf(
                            "-P",
                            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                                buildDir.absolutePath + "/compose-metrics"
                        )
                }
            }
        }
    }
}
