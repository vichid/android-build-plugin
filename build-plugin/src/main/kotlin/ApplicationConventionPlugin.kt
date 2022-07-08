import io.github.vichid.AndroidConfiguration.configureAppAndroid
import io.github.vichid.AnvilConfiguration.configureAnvil
import io.github.vichid.AppVersioningConfiguration.configureAppVersioning
import io.github.vichid.LintConfiguration.configureLint
import io.github.vichid.RulerConfiguration.configureRuler
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                withPlugin("com.android.application", configureAppVersioning())
                withPlugin("com.android.application", configureAppAndroid())
                withPlugin("com.android.application", configureLint())
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
                apply("io.github.vichid.kotlin")
                apply("com.spotify.ruler")
                withPlugin("com.spotify.ruler", configureRuler())
                apply("com.squareup.anvil")
                withPlugin("com.squareup.anvil", configureAnvil())
            }
        }
    }
}
