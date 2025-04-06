import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.junit5)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.firebase.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.enjot.materialweather"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
        compose = true
    }

    defaultConfig {
        applicationId = "com.enjot.materialweather"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0-beta01"

        testInstrumentationRunner = "com.enjot.materialweather.KoinTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        // Read API Key from local.properties
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            val localProperties = Properties().apply {
                load(localPropertiesFile.inputStream())
            }

            // Adding API keys to the build config
            buildConfigField(
                "String",
                "API_KEY_OPENWEATHERMAP",
                "\"${localProperties.getProperty("api_key_openweathermap")}\""
            )
            buildConfigField(
                "String",
                "API_KEY_GEOAPIFY",
                "\"${localProperties.getProperty("api_key_geoapify")}\""
            )
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    composeCompiler {
        includeSourceInformation = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

configurations.getByName("androidTestImplementation") {
    exclude(group = "io.mockk", module = "mockk-agent-jvm")
}

dependencies {

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.work.runtime)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(libs.androidx.glance)
    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.runtime.compose)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.assertk)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.animation.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.assertk)
    androidTestImplementation(libs.turbine)
    debugImplementation(libs.androidx.compose.ui.tooling)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    // Networking
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.ktor)
    testImplementation(libs.ktor.client.mock)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    kspAndroidTest(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)

    // Dependency Injection
    implementation(libs.bundles.koin)
    testImplementation(libs.bundles.koin.test)
    androidTestImplementation(libs.koin.test.android)

    // Location services
    implementation(libs.play.services.location)

    // Coil
    implementation(libs.coil.compose)

    // Logging
    implementation(libs.timber)

    debugImplementation(libs.leakCanary)
}

ksp {
    arg("room.generateKotlin", "true")
    arg("room.schemaLocation", "$projectDir/schemas")
}