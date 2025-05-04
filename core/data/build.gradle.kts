plugins {
    alias(libs.plugins.materialweather.android.library)
    alias(libs.plugins.materialweather.jvm.ktor)
}

android { namespace = "pl.audiotocom.enjot.materialweather.core.data" }

dependencies {
    implementation(projects.core.domain)

    implementation(libs.play.services.location)
    implementation(libs.timber)
    implementation(libs.bundles.koin)
    implementation(libs.androidx.datastore.preferences)
}