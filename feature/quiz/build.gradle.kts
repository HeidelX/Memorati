plugins {
    id("memorati.android.feature")
    id("memorati.android.library.compose")
    id("memorati.android.library.jacoco")
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.memorati.feature.quiz"
    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":core:algorithm"))
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.core.ktx)
}