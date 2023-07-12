plugins {
    id("memorati.android.library")
    id("memorati.android.library.jacoco")
    kotlin("kapt")
}

android {
    namespace = "com.memorati.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(project( ":core:common"))

    kapt(libs.hilt.compiler)

    testImplementation(project(":core:testing"))
}