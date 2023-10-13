@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    kotlin("kapt")
}

android {
    namespace = "com.taehoon.mvi.presentation"
    compileSdk = libs.versions.sdk.comple.get().toInt()

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(libs.hilt)
    kapt(libs.hilt.kapt)

    implementation(libs.androidx.paging)

    testImplementation(libs.bundles.test)

    implementation(project(":common"))
    implementation(project(":feature:domain"))
}