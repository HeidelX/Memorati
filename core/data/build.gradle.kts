plugins {
    id("memorati.android.library")
    id("memorati.android.library.jacoco")
    id("memorati.android.hilt")
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.memorati.core.data"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":core:db"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.core.ktx)
}