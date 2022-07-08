package io.github.vichid.reporter

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.github.vichid.RulerReport
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class MixpanelReporterTask : DefaultTask() {

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val input: RegularFileProperty

    @TaskAction
    fun report() {
        val mixpanelToken: String = System.getenv("MIXPANEL_TOKEN")
            ?: throw IllegalStateException("MIXPANEL_TOKEN must be configured")
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val fileAsString =
            input.asFile.orNull?.readText() ?: throw IllegalStateException("Input file is empty")
        val rulerReport: RulerReport = moshi.adapter(RulerReport::class.java).fromJson(fileAsString)
            ?: throw IllegalStateException("Input file is not a valid RulerReport")

        val reporter = MixpanelReporter(mixpanelToken)
        reporter.report(
            MixpanelEvent(
                "component_size_${rulerReport.name}",
                mapOf(
                    "download_size" to rulerReport.downloadSize,
                    "install_size" to rulerReport.installSize
                )
            )
        )
        rulerReport.components
            .filter { it.type == "INTERNAL" }
            .forEach {
                reporter.report(
                    MixpanelEvent(
                        "component_size_${it.name}",
                        mapOf(
                            "download_size" to it.downloadSize,
                            "install_size" to it.installSize
                        )
                    )
                )
            }
    }
}
