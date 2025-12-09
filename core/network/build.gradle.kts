plugins {
    id("compose.taptap.android.library")
    id("compose.taptap.android.koin")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.compose.taptap.core.network"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(projects.core.model)
    //coroutines
    implementation(libs.kotlinx.coroutines.android)

    //network
    api(libs.bundles.ktor)
    api(libs.ktor.network)
    api(libs.ktor.android)

    implementation(libs.kotlinx.serialization.json)
}
