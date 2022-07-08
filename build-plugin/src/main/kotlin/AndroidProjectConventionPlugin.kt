import io.github.vichid.AutoManifestConfiguration.configureAutoManifest
import io.github.vichid.DependencyUpdatesConfiguration.configureDependencyUpdates
import io.github.vichid.GraphRulesConfiguration.configureGraphRules
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidProjectConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("base")
                apply("com.autonomousapps.dependency-analysis")
                apply("com.osacky.doctor")
                apply("com.github.ben-manes.versions")
                withPlugin("com.github.ben-manes.versions", configureDependencyUpdates())
                apply("io.github.vichid.module")
                apply("com.jraska.module.graph.assertion")
                withPlugin("com.jraska.module.graph.assertion", configureGraphRules())
                apply("org.jetbrains.kotlinx.kover")
                apply("com.gradleup.auto.manifest")
                withPlugin("com.gradleup.auto.manifest", configureAutoManifest())
            }
        }
    }
}
