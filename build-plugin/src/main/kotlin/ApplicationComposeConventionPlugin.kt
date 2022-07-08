import io.github.vichid.ComposeConfiguration.configureComposeApp
import io.github.vichid.KspConfiguration.configureKsp
import io.github.vichid.ShowkaseConfiguration.configureShowkase
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("io.github.vichid.application")
                withPlugin("io.github.vichid.application", configureComposeApp())
                apply("com.google.devtools.ksp")
                withPlugin("com.google.devtools.ksp", configureKsp())
                withPlugin("com.google.devtools.ksp", configureShowkase())
            }
        }
    }
}
