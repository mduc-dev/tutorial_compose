plugins {
    id("compose.taptap.android.feature")
    id("compose.taptap.android.koin")
}

android {
    namespace = "com.compose.taptap.feature.game"
}
dependencies{
    //TODO: sua lai cho nay dang import du
    implementation(libs.androidx.paging.compose)
    implementation(projects.feature.search)
    implementation(projects.core.model)
    implementation(projects.core.domain)
    implementation(projects.core.network)
}
