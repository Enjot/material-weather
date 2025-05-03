
import com.android.build.api.dsl.ApplicationExtension
import com.enjot.materialweather.convention.ExtensionType
import com.enjot.materialweather.convention.configureBuildTypes
import com.enjot.materialweather.convention.configureKotlinAndroid
import com.enjot.materialweather.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure


class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {

                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()

                    targetSdk = libs.findVersion("projectTargetSdk").get().toString().toInt()

                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }

                buildFeatures {
                    buildConfig = true
                }

                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION
                )
            }
        }
    }
}