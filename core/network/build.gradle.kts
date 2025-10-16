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
    implementation(projects.core.model)
    //coroutines
    implementation(libs.kotlinx.coroutines.android)

    //network
    implementation(libs.bundles.ktor)
    implementation(libs.ktor.android)

    implementation(libs.kotlinx.serialization.json)
}