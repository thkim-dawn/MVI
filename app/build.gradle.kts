@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    kotlin("kapt")
}

android {
    namespace = "com.taehoon.mvi"
    compileSdk = libs.versions.sdk.comple.get().toInt()

    defaultConfig {
        applicationId = "com.taehoon.mvi"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        versionCode = 1
        versionName = "1.0"
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

        freeCompilerArgs = freeCompilerArgs + listOf("-Xopt-in=kotlin.RequiresOptIn", "-Xopt-in=kotlin.OptIn")
    }
    viewBinding.enable = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.splash)
    implementation(libs.bundles.kotlin)
    implementation(libs.hilt)
    kapt(libs.hilt.kapt)

    implementation(libs.bundles.retrofit)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.lib)
    implementation(libs.okhttp.logging)

    implementation(project(":common"))
    implementation(project(":feature:datasource"))
    implementation(project(":feature:domain"))
    implementation(project(":feature:main"))
    implementation(project(":feature:presentation"))
    implementation(project(":feature:repository"))
}