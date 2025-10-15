package com.compose.taptap

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
  commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
  commonExtension.apply {
    compileSdk = 36

    defaultConfig {
      minSdk = 21
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_21
      targetCompatibility = JavaVersion.VERSION_21
    }

    lint {
      abortOnError = false
    }
  }
}

internal fun Project.configureKotlinAndroid(
  extension: KotlinAndroidProjectExtension,
) {
  extension.apply {
    compilerOptions {
      // Treat all Kotlin warnings as errors (disabled by default)
      allWarningsAsErrors.set(
        properties["warningsAsErrors"] as? Boolean ?: false
      )

      jvmTarget.set(JvmTarget.JVM_21)
    }
  }
}
