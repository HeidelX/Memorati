plugins {
    id("memorati.android.library")
    id("memorati.android.library.jacoco")
}

android {
    namespace = "com.memorati.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project( ":core:common"))
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(project(":core:testing"))
}