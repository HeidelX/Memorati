plugins {
    id("memorati.android.library")
    id("memorati.android.library.jacoco")
    id("memorati.android.hilt")
    id("memorati.android.room")
    id("kotlinx-serialization")
}

android {
    namespace = "com.memorati.core.db"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(project(":core:common"))
}