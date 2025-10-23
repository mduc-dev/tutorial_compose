plugins {
    id("compose.taptap.android.library")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.compose.taptap.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.navigation)
    implementation(projects.core.database)
    implementation(projects.core.designsystem)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.paging.compose)

    // di
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.composeViewModel)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

}