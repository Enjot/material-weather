plugins {
    alias(libs.plugins.materialweather.android.library.compose)
}

android {
    namespace = "com.enjot.materialweather.widget"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.weather.domain)
    implementation(projects.weather.presentation)

    implementation(libs.androidx.glance)
    implementation(libs.androidx.glance.preview)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.appwidget.preview)
    implementation(libs.androidx.glance.material3)

    implementation(libs.bundles.koin)
    implementation(libs.timber)
}