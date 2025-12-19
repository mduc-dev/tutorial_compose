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
    
    implementation(libs.kotlinx.immutable.collections)

    debugImplementation(libs.androidx.compose.ui.tooling)

    api(libs.media3.exoplayer)
    api(libs.media3.ui)
    api(libs.media3.ui.compose)
    api(libs.media3.database)
    api(libs.media3.datasource)
    testImplementation(libs.junit)
}
