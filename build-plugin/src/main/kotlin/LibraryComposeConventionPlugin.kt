import io.github.vichid.ComposeConfiguration.configureComposeLibrary
import io.github.vichid.KspConfiguration.configureKsp
import io.github.vichid.PaparazziConfiguration.configurePaparazzi
import io.github.vichid.ShowkaseConfiguration.configureShowkase
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

class LibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("io.github.vichid.library")
                withPlugin("io.github.vichid.library", configureComposeLibrary())
                apply("app.cash.paparazzi")
                withPlugin("app.cash.paparazzi", configurePaparazzi())
                apply("com.google.devtools.ksp")
                withPlugin("com.google.devtools.ksp", configureKsp())
                withPlugin("com.google.devtools.ksp", configureShowkase())
            }

            tasks.withType<Test>().configureEach {
                this.jvmArgs("--add-opens=java.base/java.lang.reflect=ALL-UNNAMED")
            }
        }
    }
}
