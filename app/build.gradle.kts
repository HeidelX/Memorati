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
    compileSdk = 34

    val versionMajor: String by project
    val versionMinor: String by project
    val versionPatch: String by project
    val versionBuild: String by project


    defaultConfig {
        applicationId = "com.memorati"
        minSdk = 24
        targetSdk = 34
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
    lint {
        disable += "MutableCollectionMutableState"
    }
}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
    implementation(project(":core:design"))
    implementation(project(":feature:cards"))
    implementation(project(":feature:favourites"))
    implementation(project(":feature:creation"))
    implementation(project(":feature:assistant"))
    implementation(project(":feature:settings"))

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.profileinstaller)
    implementation(project(mapOf("path" to ":core:common")))

    debugImplementation(libs.androidx.compose.ui.testManifest)

    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.accompanist.testharness)
    androidTestImplementation(kotlin("test"))
}