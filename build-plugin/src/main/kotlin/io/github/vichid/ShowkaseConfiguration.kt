package io.github.vichid

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

object ShowkaseConfiguration {
    fun Project.configureShowkase(): (AppliedPlugin).() -> Unit =
        {
            val libs: VersionCatalog =
                project.extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "debugImplementation"(libs.findLibrary("showkase").get().get())
                "kspDebug"(libs.findLibrary("showkaseProcessor").get().get())
            }
        }
}
