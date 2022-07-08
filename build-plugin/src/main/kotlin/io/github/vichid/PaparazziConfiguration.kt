package io.github.vichid

import org.gradle.api.Project
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.api.plugins.JavaBasePlugin

object PaparazziConfiguration {
    fun Project.configurePaparazzi(): (AppliedPlugin).() -> Unit =
        {
            tasks.named(JavaBasePlugin.CHECK_TASK_NAME) {
                dependsOn("verifyPaparazziDebug")
            }
        }
}
