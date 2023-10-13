@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.java.library.get().pluginId)
    id(libs.plugins.kotlinjvm.get().pluginId)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlin)
    implementation(libs.coroutine.jvm)
    implementation(libs.androidx.paging.common)

    testImplementation(libs.bundles.test)
}
