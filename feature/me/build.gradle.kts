plugins {
    id("compose.taptap.android.feature")
    id("compose.taptap.android.koin")
}

android {
    namespace = "com.compose.taptap.feature.me"
    extensions.configure<org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension> {
        metricsDestination = project.layout.buildDirectory.dir("compose_metrics")
    }
}

dependencies {
    implementation(project(":feature:auth"))
    implementation(projects.core.preview)

    implementation(libs.bundles.coil)
    implementation(libs.kotlinx.immutable.collections)
    implementation(libs.kotlinx.serialization.json)
}
