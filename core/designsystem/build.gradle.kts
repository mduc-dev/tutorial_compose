plugins {
    id("compose.taptap.android.library")
    id("compose.taptap.android.library.compose")
}

android {
    namespace = "com.compose.taptap.core.designsystem"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.navigation)

    api(libs.bundles.coil)

    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.animation)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.appcompat)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
