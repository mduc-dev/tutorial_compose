plugins {
    id("compose.taptap.android.library")
    id("compose.taptap.android.koin")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.compose.taptap.core.database"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)

}
