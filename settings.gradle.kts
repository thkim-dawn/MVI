pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "mvi"
include(":app")
include(":common")

include(":feature:main")
include(":feature:presentation")
include(":feature:datasource")
include(":feature:repository")
include(":feature:domain")
