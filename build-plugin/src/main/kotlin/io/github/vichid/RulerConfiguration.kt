package io.github.vichid

import com.spotify.ruler.plugin.RulerExtension
import com.spotify.ruler.plugin.RulerTask
import io.github.vichid.reporter.MixpanelReporterTask
import org.gradle.api.Project
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType

object RulerConfiguration {
    fun Project.configureRuler(): (AppliedPlugin).() -> Unit =
        {
            extensions.configure<RulerExtension> {
                abi.set("arm64-v8a")
                locale.set("en")
                screenDensity.set(480)
                sdkVersion.set(27)
            }
            val mixpanelReporterTask by tasks.registering(MixpanelReporterTask::class) {
                input.set(buildDir.resolve("reports/ruler/release/report.json"))
            }

            tasks.withType<RulerTask>().configureEach { finalizedBy(mixpanelReporterTask) }
        }
}
