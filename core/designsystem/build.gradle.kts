plugins {
    id("compose.taptap.android.library")
    id("compose.taptap.android.library.compose")
}

android {
    namespace = "com.compose.taptap.core.designsystem"
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)
    api(libs.androidx.constraintlayout.compose)
}