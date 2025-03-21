plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.kotlinX.serialization.plugin)
    alias(libs.plugins.ktLint)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.kotlin_compose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.kotlin_compose"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.constraintlayout.compose)

    implementation(libs.kotlinX.serializationJson)

    implementation(libs.bundles.ktor)
    implementation(libs.ktor.android)
    implementation(libs.bundles.coil)

    implementation(libs.paging.compose)
    implementation(libs.paging.common)

    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.composeViewModel)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.koin.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.manifest)

    // System UI Controller
    implementation(libs.accompanist.systemuicontroller)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Extend icons
    implementation(libs.androidx.material.icons.extended)

    // Navigation Animation
    implementation(libs.accompanist.navigation.animation)
}
