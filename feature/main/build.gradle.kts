@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    kotlin("kapt")
}

android {
    namespace = "com.taehoon.mvi.main"
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
    viewBinding.enable = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.paging)

    implementation(libs.material)
    implementation(libs.bundles.kotlin)
    implementation(libs.hilt)
    kapt(libs.hilt.kapt)

    implementation(libs.glide)

    implementation(libs.bundles.retrofit)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.lib)
    implementation(libs.okhttp.logging)

    implementation(project(":common"))
    implementation(project(":feature:presentation"))
}