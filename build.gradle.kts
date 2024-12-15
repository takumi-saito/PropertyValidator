// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // android
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    // kotlin
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false

    // ksp
    alias(libs.plugins.ksp) apply false
    id("maven-publish")
}

group = "t.saito.com.lib" // パッケージ名
version = "1.0.0" // バージョン番号