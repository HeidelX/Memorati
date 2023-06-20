plugins {
    id("memorati.android.feature")
    id("memorati.android.library.compose")
    id("memorati.android.library.jacoco")
}

android {
    namespace = "com.memorati.feature.assistant"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.permissions)

    // Work
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
    kapt(libs.hilt.ext.compiler)
    
    androidTestImplementation(libs.androidx.work.testing)
}