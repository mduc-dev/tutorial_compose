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

    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)
    api(libs.androidx.constraintlayout.compose)
    implementation(libs.bundles.coil)
    api(libs.androidx.material.icons.extended)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.appcompat)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
