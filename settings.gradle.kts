@file:Suppress("UnstableApiUsage")

include(":core:designsystem")


enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
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

include(":feature:game")
include(":feature:search")
