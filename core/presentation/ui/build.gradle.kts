plugins { alias(libs.plugins.materialweather.android.library.compose) }

android { namespace = "com.enjot.materialweather.core.presentation.ui" }

dependencies {
    implementation(projects.core.domain)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
}