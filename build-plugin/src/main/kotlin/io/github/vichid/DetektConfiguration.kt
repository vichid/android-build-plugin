package io.github.vichid

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType

object DetektConfiguration {
    fun Project.configureDetekt(): (AppliedPlugin).() -> Unit =
        {
            val reportXmlMerge by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
                output.set(rootProject.buildDir.resolve("reports/detekt/merge.xml"))
            }
            val reportSarifMerge by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
                output.set(rootProject.buildDir.resolve("reports/detekt/merge.sarif"))
            }
            reportXmlMerge.configure {
                input.from("${buildDir.absolutePath}/reports/detekt/detekt.xml")
            }
            reportSarifMerge.configure {
                input.from("${buildDir.absolutePath}/reports/detekt/detekt.sarif")
            }
            tasks.withType<Detekt>().configureEach {
                config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
                buildUponDefaultConfig = true
                reports {
                    xml.required.set(true)
                    sarif.required.set(true)
                }
                source(
                    objects.fileCollection().from(
                        DetektExtension.DEFAULT_SRC_DIR_JAVA,
                        DetektExtension.DEFAULT_TEST_SRC_DIR_JAVA,
                        DetektExtension.DEFAULT_SRC_DIR_KOTLIN,
                        DetektExtension.DEFAULT_TEST_SRC_DIR_KOTLIN,
                    )
                )
                finalizedBy(reportXmlMerge)
                finalizedBy(reportSarifMerge)
            }

            val libs: VersionCatalog =
                project.extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "detektPlugins"(libs.findLibrary("detekt.formatting").get().get())
                "detektPlugins"(libs.findLibrary("detekt.custom.rules").get().get())
            }
        }
}
