import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.compose.taptap.Configuration

plugins {
    id("compose.taptap.android.application")
    id("compose.taptap.android.application.compose")
    id("compose.taptap.android.koin")
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.ktLint)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.androidx.baselineprofile)
}

android {
    namespace = "com.compose.taptap"

    defaultConfig {
        val buildNumber = System.getProperty("BUILD_NUMBER")?.toIntOrNull() ?: Configuration.versionCode
        val buildVersion = System.getProperty("BUILD_VERSION") ?: Configuration.versionName

        applicationId = "com.compose.taptap"
        versionCode = buildNumber
        versionName = buildVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        ndk {
            //noinspection ChromeOsAbiSupport
            abiFilters += "arm64-v8a"
        }
    }

    signingConfigs {
        create("release") {
            val keystorePath = System.getenv("KEYSTORE_PATH") ?: "../keystore_release.jks"
            storeFile = file(keystorePath)
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            ndk {
                debugSymbolLevel = "NONE"
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

    baselineProfile {
        // This specifies the module that generates the baseline profile.
        // It must be a com.android.test module.
        saveInSrc = true
        from(project(":benchmark"))
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    // Feature modules
    implementation(project(":feature:game"))
    implementation(project(":feature:search"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:play"))
    implementation(project(":feature:me"))
    implementation(project(":feature:game_detail"))
    implementation(project(":feature:notifications"))
    implementation(project(":feature:tavern"))
    implementation(project(":feature:settings"))
    
    // Core modules
    implementation(projects.core.navigation)
    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.core.data)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.viewmodel)
    implementation(projects.core.designsystem)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.constraintlayout.compose)

    implementation(libs.bundles.coil)
    implementation(libs.androidx.profileinstaller)

    implementation(libs.media3.exoplayer)
    implementation(libs.media3.exoplayer.dash)
    implementation(libs.media3.ui)
    implementation(libs.media3.ui.compose)
    
    implementation(libs.kotlinx.serialization.json)


    implementation(libs.androidx.paging.compose)

    //unit test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.manifest)

    // Extend icons
    implementation(libs.androidx.material.icons.extended)

}
