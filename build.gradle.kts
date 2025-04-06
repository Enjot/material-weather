// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlin) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.firebase.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}