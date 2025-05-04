plugins {
    alias(libs.plugins.materialweather.android.application.compose)
    alias(libs.plugins.materialweather.jvm.ktor)
    alias(libs.plugins.materialweather.jvm.testing)
    alias(libs.plugins.ksp)
    alias(libs.plugins.firebase.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.enjot.materialweather"

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.ui)
    implementation(projects.core.presentation.designsystem)

    implementation(projects.weather.data)
    implementation(projects.weather.domain)
    implementation(projects.weather.presentation)

    implementation(projects.settings.data)
    implementation(projects.settings.domain)
    implementation(projects.settings.presentation)

    implementation(projects.widget)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.appcompat)
    implementation(libs.kotlinx.coroutines.guava)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.bundles.koin)

    implementation(libs.timber)

    debugImplementation(libs.leakCanary)
}