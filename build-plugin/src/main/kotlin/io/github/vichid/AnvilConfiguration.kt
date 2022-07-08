package io.github.vichid

import com.squareup.anvil.plugin.AnvilExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

object AnvilConfiguration {
    fun Project.configureAnvil(): (AppliedPlugin).() -> Unit =
        {
            extensions.configure<AnvilExtension> {
                generateDaggerFactories.set(true)
            }
            val libs: VersionCatalog =
                project.extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies.add("implementation", libs.findLibrary("dagger").get().get())
        }
}
