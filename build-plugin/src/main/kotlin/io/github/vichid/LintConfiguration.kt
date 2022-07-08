package io.github.vichid

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.kotlin.dsl.getByType

object LintConfiguration {

    @Suppress("UnstableApiUsage")
    fun Project.configureLint(): (AppliedPlugin).() -> Unit =
        {
            val extension = extensions.getByType<BaseAppModuleExtension>()
            extension.lint {
                warningsAsErrors = true
                checkDependencies = true
                abortOnError = true
                ignoreTestSources = true
            }
        }

    fun Project.disableLintFromCheck(): (AppliedPlugin).() -> Unit =
        {
            tasks.named(JavaBasePlugin.CHECK_TASK_NAME).configure {
                setDependsOn(
                    dependsOn.filterNot { it.toString().contains("AndroidLintGlobalTask") }
                        .toList()
                )
            }
        }
}
