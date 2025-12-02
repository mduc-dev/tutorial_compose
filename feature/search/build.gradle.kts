plugins {
    id("compose.taptap.android.feature")
    id("compose.taptap.android.koin")
}

android {
    namespace = "com.compose.taptap.feature.search"
}
//TODO: sua lai cho nay dang import sai, giong voi game screen
dependencies {
    // Compose UI Tooling for Preview
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
}
