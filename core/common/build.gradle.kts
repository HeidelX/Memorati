plugins {
    id("memorati.android.library")
    id("memorati.android.library.jacoco")
    id("memorati.android.hilt")
}

android {
    namespace = "com.memorati.core.common"

    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.accompanist.permissions)
}