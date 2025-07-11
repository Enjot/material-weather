
import com.enjot.materialweather.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class JvmTestingConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {

            dependencies {
                "testImplementation"(libs.findLibrary("junit.jupiter.api").get())
                "testRuntimeOnly"(libs.findLibrary("junit.jupiter.engine").get())
                "testImplementation"(libs.findLibrary("junit.jupiter.params").get())
                "testImplementation"(libs.findLibrary("assertk").get())
                "testImplementation"(libs.findLibrary("junit").get())
                "testImplementation"(libs.findLibrary("kotlinx.coroutines.test").get())
                "testImplementation"(libs.findLibrary("mockk").get())
                "testImplementation"(libs.findLibrary("mockk.agent").get())
                "testImplementation"(libs.findLibrary("mockwebserver").get())
                "testImplementation"(libs.findLibrary("turbine").get())
                "testImplementation"(libs.findLibrary("koin.test").get())
            }

            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
            }
        }
    }
}