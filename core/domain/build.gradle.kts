plugins {
    id("compose.taptap.android.library")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.compose.taptap.core.domain"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)

}