package io.github.vichid

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.TestExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.getByType

object AndroidConfiguration {

    fun Project.configureAppAndroid(): (AppliedPlugin).() -> Unit =
        {
            val extension = extensions.getByType<BaseAppModuleExtension>()
            extension.configureAndroid(providers)
            extension.configureApp()
        }

    fun Project.configureLibraryAndroid(): (AppliedPlugin).() -> Unit =
        {
            extensions.getByType<LibraryExtension>().configureAndroid(providers)
        }

    fun Project.configureTestAndroid(): (AppliedPlugin).() -> Unit =
        {
            extensions.getByType<TestExtension>().configureAndroid(providers)
        }

    private fun getAndroidConf(providers: ProviderFactory): AndroidConf {
        val compileSdk = providers.gradleProperty("app.compileSdk")
            .map { it.toInt() }
            .orNull ?: missing("app.compileSdk")
        val minSdk = providers.gradleProperty("app.minSdk")
            .map { it.toInt() }
            .orNull ?: missing("app.minSdk")
        val targetSdk = providers.gradleProperty("app.targetSdk")
            .map { it.toInt() }
            .orNull ?: missing("app.targetSdk")
        return AndroidConf(compileSdk, minSdk, targetSdk)
    }

    @Suppress("UnstableApiUsage")
    private fun CommonExtension<*, *, *, *>.configureAndroid(providers: ProviderFactory) {
        val androidConf = getAndroidConf(providers)
        compileSdk = androidConf.compileSdk
        namespace = providers.gradleProperty("app.namespace")
            .orNull ?: missing("app.namespace")

        defaultConfig.minSdk = androidConf.minSdk
        defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        when (this) {
            is BaseAppModuleExtension -> defaultConfig.targetSdk = androidConf.targetSdk
        }
    }

    @Suppress("UnstableApiUsage")
    private fun BaseAppModuleExtension.configureApp() {
        buildTypes {
            getByName("debug") {
                applicationIdSuffix = ".debug"
                isDefault = true
            }
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
                )
                signingConfig = signingConfigs.getByName("debug")
            }
        }
    }

    data class AndroidConf(
        val compileSdk: Int,
        val minSdk: Int,
        val targetSdk: Int
    )
}
