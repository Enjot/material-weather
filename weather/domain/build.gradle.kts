plugins {
    alias(libs.plugins.materialweather.jvm.library)
    alias(libs.plugins.materialweather.jvm.testing)
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.kotlinx.coroutines.core)
}