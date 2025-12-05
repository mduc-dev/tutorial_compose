@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "taptap-compose"

include(":app")
include(":core:designsystem")
include(":core:navigation")
include(":core:model")
include(":core:domain")
include(":core:data")
include(":core:database")
include(":core:network")
include(":core:preview")
include(":core:viewmodel")

include(":feature:auth")
include(":feature:game")
include(":feature:play")
include(":feature:search")
include(":feature:account")
include(":feature:game_detail")
include(":feature:notifications")
include(":feature:tavern")
