plugins {
    alias(libs.plugins.materialweather.android.library)
    alias(libs.plugins.materialweather.android.library.testing)
}

android {
    namespace = "com.enjot.materialweather.settings.data"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.settings.domain)
    implementation(projects.core.domain)
    implementation(projects.weather.domain)


    implementation(libs.androidx.datastore.preferences)
    implementation(libs.timber)
    implementation(libs.androidx.work.runtime)
    implementation(libs.bundles.koin)
}