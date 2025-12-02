plugins {
    id("compose.taptap.android.feature")
    id("compose.taptap.android.koin")
}

android {
    namespace = "com.compose.taptap.feature.search"
}
//TODO: sua lai cho nay dang import sai, giong voi game screen
dependencies {
    // Core modules
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.network)

    // ViewModel & Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Koin
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.composeViewModel)

    // Compose UI Tooling for Preview
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
}