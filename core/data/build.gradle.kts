plugins {
    id("compose.taptap.android.library")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.compose.taptap.core.data"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}