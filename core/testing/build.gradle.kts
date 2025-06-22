plugins {
    id("memorati.android.library")
    id("memorati.android.library.compose")
    id("memorati.android.hilt")
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.memorati.core.testing"
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    api(libs.androidx.compose.ui.test)
    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.hilt.android.testing)
    api(libs.junit4)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)
    implementation(libs.androidx.core.ktx)

    debugApi(libs.androidx.compose.ui.testManifest)

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.kotlinx.datetime)
}
