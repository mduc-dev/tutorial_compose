plugins {
    id("compose.taptap.android.library")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.compose.taptap.core.model"
}

dependencies {
    api(libs.kotlinx.serialization.json)
    
    // Compose runtime for @Immutable/@Stable annotations
    // This is lightweight and only adds annotation support
    implementation("androidx.compose.runtime:runtime:1.7.6")
}
