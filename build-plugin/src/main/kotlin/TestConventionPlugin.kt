import io.github.vichid.AndroidConfiguration.configureTestAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class TestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                withPlugin("com.android.test", configureTestAndroid())
                apply("org.jetbrains.kotlin.android")
                apply("io.github.vichid.kotlin")
            }
        }
    }
}
