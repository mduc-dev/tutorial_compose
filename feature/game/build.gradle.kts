plugins {
    id("compose.taptap.android.feature")
    id("compose.taptap.android.koin")
}

android {
    namespace = "com.compose.taptap.feature.game"
}
dependencies{
    implementation(libs.androidx.paging.compose)
    implementation(libs.kotlinx.immutable.collections)
}
