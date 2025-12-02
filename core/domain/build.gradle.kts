plugins {
    id("compose.taptap.android.library")
}

android {
    namespace = "com.compose.taptap.core.domain"
}

dependencies {
    api(projects.core.model)
    api(libs.androidx.paging.common)
    implementation(libs.kotlinx.coroutines.core)
}
