package io.github.vichid

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

object ComposeReportMerger {

    fun mergeTxt(inputs: Collection<File>, output: File) {
        val writer = FileWriter(output, true)
        val bw = BufferedWriter(writer)
        inputs.forEach {
            bw.write(it.readText())
            bw.newLine()
        }
        bw.close()
        writer.close()
    }

    fun mergeCsv(inputs: Collection<File>, output: File) {
        val writer = FileWriter(output, true)
        val bw = BufferedWriter(writer)
        if (output.length() != 0L) {
            inputs.forEach { file ->
                file.readLines()
                    .drop(1)
                    .forEach {
                        bw.write(it)
                        bw.newLine()
                    }
            }
        } else {
            inputs.forEach {
                bw.write(it.readText())
            }
        }
        bw.close()
        writer.close()
    }
}
