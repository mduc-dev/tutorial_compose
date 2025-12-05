plugins {
    id("compose.taptap.android.feature")
    id("compose.taptap.android.koin")
}

android {
    namespace = "com.compose.taptap.feature.play"
}

dependencies {
    implementation(libs.androidx.paging.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.bundles.coil)
}
