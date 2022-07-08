import io.github.vichid.AnvilConfiguration.configureAnvil
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("io.github.vichid.kotlin")
                withPlugin("com.squareup.anvil", configureAnvil())
            }
        }
    }
}
