pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MaterialWeather"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":settings:data")
include(":settings:domain")
include(":settings:presentation")
include(":core:domain")
include(":core:data")
include(":core:presentation:designsystem")
include(":core:presentation:ui")
include(":weather:data")
include(":weather:domain")
include(":weather:presentation")
include(":widget")
