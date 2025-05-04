plugins {
    alias(libs.plugins.materialweather.android.feature.ui)
    alias(libs.plugins.materialweather.android.library.testing)
}

android {
    namespace = "com.enjot.materialweather.weather.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.presentation.ui)
    implementation(projects.weather.domain)
}