package io.github.vichid

import com.gradleup.auto.manifest.AutoManifestExtension
import org.gradle.api.Project
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.kotlin.dsl.configure

object AutoManifestConfiguration {
    fun Project.configureAutoManifest(): (AppliedPlugin).() -> Unit =
        {
            extensions.configure<AutoManifestExtension> {
                packageName.set("com.example.playground")
            }
        }
}
