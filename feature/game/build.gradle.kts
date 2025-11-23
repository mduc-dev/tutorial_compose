plugins {
    id("compose.taptap.android.library")
    id("compose.taptap.android.library.compose")
}

android {
    namespace = "com.compose.taptap.feature.game"
}

dependencies {
    // Feature modules
    implementation(projects.feature.search)
    
    // Core modules
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.navigation)
    implementation(projects.core.data) // For RefreshTrigger
    implementation(projects.core.network) // For SearchResponse
    
    // Material Icons
    implementation(libs.androidx.material.icons.extended)
    
    // ViewModel & Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    
    // Paging
    implementation(libs.androidx.paging.compose)
    
    // Koin
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.composeViewModel)
    
    // Coil
    implementation(libs.bundles.coil)
    
    // Compose UI Tooling for Preview
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
}
