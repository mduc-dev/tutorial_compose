plugins {
    id("compose.taptap.android.application")
    id("compose.taptap.android.application.compose")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.compose.taptap.core.navigation"
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.coroutines.android)

    api(libs.androidx.navigation.compose)

    implementation(libs.kotlinx.serialization.json)
}