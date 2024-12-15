// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    // android
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    // compose (only Kotlin 2.0)
    // alias(libs.plugins.compose.compiler) apply false

    // kotlin
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false

    // kapt
    alias(libs.plugins.kapt) apply false

    // ksp
    alias(libs.plugins.ksp) apply false
}
true // Needed to make the Suppress annotation work for the plugins block