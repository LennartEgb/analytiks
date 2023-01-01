plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
        version = 1

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles.add(file("consumer-rules.pro"))
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
    namespace = "de.lennartegb.analytiks_android_log"
}

dependencies {
    implementation(project(path = ":analytiks"))
    testImplementation(dependencyNotation = "junit:junit:4.13.2")
}
