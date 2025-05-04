plugins {
    alias(libs.plugins.materialweather.android.library)
    alias(libs.plugins.materialweather.android.room)
    alias(libs.plugins.materialweather.jvm.ktor)
    alias(libs.plugins.materialweather.android.library.testing)
}

android {
    namespace = "com.enjot.materialweather.weather.data"

    defaultConfig {
        
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.weather.domain)
    implementation(projects.widget)

    implementation(libs.bundles.koin)
    implementation(libs.timber)
}