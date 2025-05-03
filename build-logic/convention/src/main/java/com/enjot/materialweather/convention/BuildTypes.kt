package com.enjot.materialweather.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val propertiesProvider = gradleLocalProperties(rootDir, project.providers)
        val openWeatherMapApiKey = propertiesProvider.getProperty("api_key_openweathermap")
        val geoapifyApiKey = propertiesProvider.getProperty("api_key_geoapify")

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(
                                openWeatherMapApiKey = openWeatherMapApiKey,
                                geoapifyApiKey = geoapifyApiKey
                            )
                        }
                        release {
                            configureReleaseBuildType(
                                commonExtension = commonExtension,
                                openWeatherMapApiKey = openWeatherMapApiKey,
                                geoapifyApiKey = geoapifyApiKey
                            )
                            signingConfig = signingConfigs.getByName("debug")
                        }
                    }
                }
            }
            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(
                                openWeatherMapApiKey = openWeatherMapApiKey,
                                geoapifyApiKey = geoapifyApiKey
                            )
                        }
                        release {
                            configureReleaseBuildType(
                                commonExtension = commonExtension,
                                openWeatherMapApiKey = openWeatherMapApiKey,
                                geoapifyApiKey = geoapifyApiKey
                            )
                            signingConfig = signingConfigs.getByName("debug")
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(
    openWeatherMapApiKey: String,
    geoapifyApiKey: String
) {
    buildConfigField("String", "API_KEY_OPENWEATHERMAP", "\"$openWeatherMapApiKey\"")
    buildConfigField("String", "API_KEY_GEOAPIFY", "\"$geoapifyApiKey\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    openWeatherMapApiKey: String,
    geoapifyApiKey: String
) {
    buildConfigField("String", "API_KEY_OPENWEATHERMAP", "\"$openWeatherMapApiKey\"")
    buildConfigField("String", "API_KEY_GEOAPIFY", "\"$geoapifyApiKey\"")

    isMinifyEnabled = false
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}