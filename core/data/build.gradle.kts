plugins {
    id("compose.taptap.android.library")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.compose.taptap.core.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.database)
    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.paging.common)
    implementation(libs.kotlinx.coroutines.core)

    // di
    implementation(libs.koin.core)

}
