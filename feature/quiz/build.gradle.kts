plugins {
    id("memorati.android.feature")
    id("memorati.android.library.compose")
    id("memorati.android.library.jacoco")
}

android {
    namespace = "com.memorati.feature.quiz"
    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.permissions)
}