
import com.android.build.api.dsl.LibraryExtension
import com.enjot.materialweather.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

class AndroidLibraryTestingConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("materialweather.jvm.testing")
                apply("com.google.devtools.ksp")
                apply("de.mannodermaus.android-junit5")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "com.enjot.materialweather.KoinTestRunner"
                }
            }

            dependencies {
                "androidTestImplementation"(libs.findLibrary("androidx.junit").get())
                "androidTestImplementation"(libs.findLibrary("androidx.espresso.core").get())
                "androidTestImplementation"(libs.findLibrary("androidx.work.testing").get())
                "androidTestImplementation"(libs.findLibrary("androidx.runner").get())
                "androidTestImplementation"(libs.findLibrary("androidx.rules").get())
                "androidTestImplementation"(libs.findLibrary("junit.jupiter.api").get())
                "androidTestImplementation"(libs.findLibrary("junit.jupiter.api").get())
                "testImplementation"(libs.findLibrary("mockk.android").get())
                "testImplementation"(libs.findLibrary("androidx.room.testing").get())
                "androidTestImplementation"(libs.findLibrary("mockk.android").get())
                "androidTestImplementation"(libs.findLibrary("assertk").get())
                "androidTestImplementation"(libs.findLibrary("turbine").get())
                "androidTestImplementation"(libs.findLibrary("androidx.compose.ui.test.junit4").get())
                "androidTestImplementation"(libs.findLibrary("koin.test.android").get())
                "kspAndroidTest"(libs.findLibrary("androidx.room.compiler").get())
                "androidTestImplementation"(platform(libs.findLibrary("androidx.compose.bom").get()))
            }

            configurations.getByName("androidTestImplementation") {
                exclude(group = "io.mockk", module = "mockk-agent-jvm")
            }
        }
    }
}