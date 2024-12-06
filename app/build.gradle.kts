plugins {
    id("memorati.android.application")
    id("memorati.android.application.compose")
    id("memorati.android.application.flavors")
    id("memorati.android.application.jacoco")
    id("memorati.android.hilt")
    id("jacoco")
}


android {
    namespace = "com.memorati"
    compileSdk = 35

    val versionMajor: String by project
    val versionMinor: String by project
    val versionPatch: String by project
    val versionBuild: String by project


    defaultConfig {
        applicationId = "com.memorati"
        minSdk = 24
        targetSdk = 35
        versionCode = versionMajor.toInt().times(100_00_00_00)
            .plus(versionMinor.toInt().times(100_00_00))
            .plus(versionPatch.toInt().times(100_00))
            .plus(versionBuild.toInt().times(1_00))

        println(versionCode)
        versionName = "$versionMajor.$versionMinor.$versionPatch.$versionBuild"

        println(versionName)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("int", "VERSION_CODE", versionCode.toString())
        buildConfigField("String", "VERSION_NAME", "\"$versionName\"")
        resourceConfigurations += arrayOf("de", "en", "ar")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:design"))

    implementation(project(":feature:quiz"))
    implementation(project(":feature:cards"))
    implementation(project(":feature:creation"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:assistant"))
    implementation(project(":feature:favourites"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.material3.adaptive.navigation.suite.android)
    implementation(libs.review)
    implementation(libs.review.ktx)

    debugImplementation(libs.androidx.compose.ui.testManifest)

    androidTestImplementation(kotlin("test"))
    androidTestImplementation(libs.accompanist.testharness)
    androidTestImplementation(libs.androidx.navigation.testing)
}