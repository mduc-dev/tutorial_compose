plugins {
    id("compose.taptap.android.library")
}

android {
    namespace = "com.compose.taptap.core.viewmodel"
}

dependencies {
    api(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.kotlinx.coroutines.android)
    api(libs.androidx.paging.compose)
    implementation(projects.core.model)
}
