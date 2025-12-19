plugins {
    id("compose.taptap.android.library")
    id("compose.taptap.android.library.compose")
}

android {
    namespace = "com.compose.taptap.core.preview"
}

dependencies {
    //core
    implementation(projects.core.designsystem)
    api(projects.core.model)
    implementation(libs.kotlinx.serialization.json)
}
