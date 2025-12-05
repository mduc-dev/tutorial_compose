plugins {
    id("compose.taptap.android.feature")
    id("compose.taptap.android.koin")
}

android {
    namespace = "com.compose.taptap.feature.auth"
}

dependencies {
    implementation(libs.kotlinx.collections.immutable)
}
