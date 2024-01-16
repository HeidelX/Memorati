plugins {
    id("memorati.android.library")
    id("memorati.android.library.jacoco")
    id("memorati.android.hilt")
}

android {
    namespace = "com.memorati.core.data"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":core:db"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(libs.kotlinx.datetime)
}