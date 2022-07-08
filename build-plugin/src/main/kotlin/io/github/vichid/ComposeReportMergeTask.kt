package io.github.vichid

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File

@CacheableTask
abstract class ComposeReportMergeTask : DefaultTask() {

    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val input: ConfigurableFileCollection

    @get:OutputFile
    abstract val output: RegularFileProperty

    @TaskAction
    fun merge() {
        val existingFiles = input.files.filter { it.exists() }
        fun isTxtReport(file: File): Boolean = file.name.endsWith(".txt")
        if (existingFiles.any(::isTxtReport)) {
            ComposeReportMerger.mergeTxt(existingFiles.filter(::isTxtReport), output.get().asFile)
        }
        fun isCsvReport(file: File): Boolean = file.name.endsWith(".csv")
        if (existingFiles.any(::isCsvReport)) {
            ComposeReportMerger.mergeCsv(existingFiles.filter(::isCsvReport), output.get().asFile)
        }
    }
}
