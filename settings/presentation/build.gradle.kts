plugins {
    alias(libs.plugins.materialweather.android.feature.ui)
}

android {
    namespace = "com.enjot.materialweather.settings.presentation"
}

dependencies {
    implementation(projects.settings.domain)
    implementation(projects.core.presentation.ui)
    implementation(projects.core.domain)
}