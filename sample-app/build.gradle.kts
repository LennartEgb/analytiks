plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 33
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "de.lennartegb.analytiks.sample"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles.addAll(
                listOf(
                    file(getDefaultProguardFile("proguard-android-optimize.txt")),
                    file("proguard-rules.pro")
                )
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }

    buildFeatures {
        compose = true
    }
    namespace = "de.lennartegb.analytiks"
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("com.google.android.material:material:1.7.0")

    implementation(project(":analytiks"))
    implementation(project(":analytiks-android-log"))

    implementation("androidx.compose.ui:ui:1.3.2")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")

    implementation("com.google.firebase:firebase-core:21.1.1")
}
