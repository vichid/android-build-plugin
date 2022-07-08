package io.github.vichid

import io.github.vichid.ModuleConfiguration.IMPL
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.util.Locale

open class ModuleGenerationTask : DefaultTask() {

    private var moduleInput: String? = null
    private var configurationInput: List<ModuleConfiguration>? = null

    @Option(option = "module", description = "")
    open fun setModule(moduleInput: String?) {
        this.moduleInput = moduleInput
    }

    @Option(option = "configurations", description = "")
    open fun setConfiguration(configurationInput: List<ModuleConfiguration>?) {
        this.configurationInput = configurationInput
    }

    @Input
    open fun getModule(): String? = moduleInput

    @Input
    open fun getConfigurations(): List<ModuleConfiguration>? = configurationInput

    @TaskAction
    fun generate() {
        val moduleName = moduleInput?.split(":")
            ?.map(::cleanModuleName)
            ?.filter(String::isNotEmpty)
            ?: return
        val configurationList = configurationInput ?: return
        with(project) {
            generateDirs(moduleName, configurationList)
            generateBuildGradle(moduleName, configurationList)
            generateModuleSettings(moduleName, configurationList)
        }
    }

    private fun cleanModuleName(path: String): String =
        "[^-a-z]".toRegex().replace(path.toLowerCase(Locale.ROOT), "")

    private fun Project.generateDirs(
        moduleList: List<String>,
        configurationList: List<ModuleConfiguration>
    ) {
        val dir = moduleList.joinToString("/")
        val subpackage = moduleList.joinToString(".")
        configurationList.forEach { configuration ->
            val configurationPath = "$packageName.$subpackage.$configuration"
                .replace('.', '/')
                .replace("-", "")
            mkdir("$dir/$configuration/src/main/kotlin/$configurationPath")
            if (configuration == IMPL) {
                mkdir("$dir/$configuration/src/androidTest/kotlin/$configurationPath")
                mkdir("$dir/$configuration/src/test/kotlin/$configurationPath")
            }
        }
    }

    private fun Project.generateBuildGradle(
        moduleList: List<String>,
        configurationList: List<ModuleConfiguration>
    ) {
        val regex = "-[a-zA-Z]".toRegex()
        val camelCaseModuleName = moduleList.joinToString(".") {
            regex.replace(it) { it.value.replace("-", "").toUpperCase(Locale.ROOT) }
        }

        val dir = moduleList.joinToString("/")
        configurationList
            .map { configuration ->
                val stringBuilder = StringBuilder()
                println(configuration)
                stringBuilder.appendConfiguration(configuration)
                println(stringBuilder.toString())
                configuration to stringBuilder.toString()
                    .replace("&s", camelCaseModuleName)
            }
            .forEach { pair ->
                file("$dir/${pair.first}/build.gradle.kts")
                    .writeText(pair.second)
            }
    }

    private fun Project.generateModuleSettings(
        moduleList: List<String>,
        configurationList: List<ModuleConfiguration>
    ) {
        val modulesFile = "modules.gradle.kts"
        val originalModules = file(modulesFile).readLines().toMutableList()
        val includeModules = moduleList.joinToString(":")

        configurationList
            .forEach { configuration ->
                val include = "include(\":$includeModules:$configuration\")"
                if (!originalModules.contains(include)) {
                    originalModules.add(include)
                }
            }

        originalModules.sort()
        file(modulesFile).writeText(originalModules.joinToString(separator = "\n", postfix = "\n"))
    }

    companion object {
        const val packageName = "com.example"
    }
}
