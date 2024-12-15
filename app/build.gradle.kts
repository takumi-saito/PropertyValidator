plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "t.saito.com.propertyvalidatorusageexample"
    compileSdk = 34

    defaultConfig {
        applicationId = "t.saito.com.propertyvalidatorusageexample"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.activity.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.foundation)
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.runtime)

    implementation(libs.room.ktx)
    implementation(libs.property.validator)
    ksp(libs.property.validator)
    // module dependency
//    implementation(project(":property_validator"))
//    "ksp"(project(":property_validator"))
}