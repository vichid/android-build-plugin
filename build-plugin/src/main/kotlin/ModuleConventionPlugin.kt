import io.github.vichid.ModuleGenerationTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class ModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            tasks.register<ModuleGenerationTask>("createModules") {
                group = "help"
                description = "Creates a new set of modules following project structure"
            }
        }
    }
}
