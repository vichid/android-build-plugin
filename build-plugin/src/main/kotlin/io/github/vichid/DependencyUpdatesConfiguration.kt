package io.github.vichid

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.Project
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.kotlin.dsl.withType
import java.util.Locale

object DependencyUpdatesConfiguration {
    fun Project.configureDependencyUpdates(): (AppliedPlugin).() -> Unit =
        {
            fun String.isNonStable(): Boolean {
                val stableKeyword =
                    listOf("RELEASE", "FINAL", "GA").any { toUpperCase(Locale.ROOT).contains(it) }
                val regex = "^[0-9,.v-]+(-r)?$".toRegex()
                val isStable = stableKeyword || regex.matches(this)
                return isStable.not()
            }

            tasks.withType<DependencyUpdatesTask>().configureEach {
                rejectVersionIf {
                    candidate.version.isNonStable()
                }

                checkForGradleUpdate = true
                outputFormatter = "json"
                outputDir = "build/dependencyUpdates"
                reportfileName = "report"
            }
        }
}
