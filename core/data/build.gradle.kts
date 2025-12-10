plugins {
    id("compose.taptap.android.library")
    id("compose.taptap.android.koin")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.compose.taptap.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.network)
    implementation(projects.core.database)
    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)
}
