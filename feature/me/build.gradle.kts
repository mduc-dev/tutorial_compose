plugins {
    id("compose.taptap.android.feature")
    id("compose.taptap.android.koin")
}

android {
    namespace = "com.compose.taptap.feature.me"
}

dependencies {
    implementation(libs.bundles.coil)
    implementation(project(":feature:auth"))
}
