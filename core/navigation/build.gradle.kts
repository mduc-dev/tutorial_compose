plugins {
    id("compose.taptap.android.library")
    id("compose.taptap.android.library.compose")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.compose.taptap.core.navigation"
}

dependencies {
    implementation(libs.androidx.core)

    api(libs.androidx.navigation3.runtime)
    api(libs.androidx.navigation3.ui)

    implementation(libs.kotlinx.serialization.json)
}