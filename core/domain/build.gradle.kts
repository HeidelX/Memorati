plugins {
    id("memorati.android.library")
    id("memorati.android.library.jacoco")
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.memorati.core.domain"
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project( ":core:common"))
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.core.ktx)

    testImplementation(project(":core:testing"))
}