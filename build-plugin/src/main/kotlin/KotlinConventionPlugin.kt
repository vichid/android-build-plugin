import io.github.vichid.DetektConfiguration.configureDetekt
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("io.gitlab.arturbosch.detekt")
                withPlugin("io.gitlab.arturbosch.detekt", configureDetekt())
            }
        }
    }
}
