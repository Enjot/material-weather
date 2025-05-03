
import com.enjot.materialweather.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmKtorConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                "implementation"(libs.findBundle("ktor").get())
                "testImplementation"(libs.findLibrary("ktor.client.mock").get())
                "androidTestImplementation"(libs.findLibrary("ktor.client.mock").get())
            }
        }
    }
}