package com.compose.taptap.core.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme =
    darkColorScheme(
        primary = TapTapTokens.darkColors.primary,
        onPrimary = TapTapTokens.darkColors.onPrimary,
        secondary = TapTapTokens.darkColors.secondary,
        onSecondary = TapTapTokens.darkColors.onSecondary,
        background = TapTapTokens.darkColors.background,
        onBackground = TapTapTokens.darkColors.onBackground,
        surface = TapTapTokens.darkColors.surface,
        onSurface = TapTapTokens.darkColors.onSurface,
        error = TapTapTokens.darkColors.error,
        onError = TapTapTokens.darkColors.onError,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = TapTapTokens.lightColors.primary,
        onPrimary = TapTapTokens.lightColors.onPrimary,
        secondary = TapTapTokens.lightColors.secondary,
        onSecondary = TapTapTokens.lightColors.onSecondary,
        background = TapTapTokens.lightColors.background,
        onBackground = TapTapTokens.lightColors.onBackground,
        surface = TapTapTokens.lightColors.surface,
        onSurface = TapTapTokens.lightColors.onSurface,
        error = TapTapTokens.lightColors.error,
        onError = TapTapTokens.lightColors.onError,
    )

object TapTapTheme {
    @Composable
    operator fun invoke(
        darkTheme: Boolean = isSystemInDarkTheme(),
        dynamicColor: Boolean = false,
        content: @Composable () -> Unit,
    ) {
        val colorScheme =
            when {
                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    val context = LocalContext.current
                    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
                }

                darkTheme -> DarkColorScheme
                else -> LightColorScheme
            }
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }

        CompositionLocalProvider(
            LocalSpacing provides Spacing(),
            LocalShapeTokens provides ShapeTokens(),
        ) {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = TapTapTypography,
                shapes = TapTapShapes,
                content = content
            )
        }
    }

    val colors: ColorScheme
        @Composable get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable get() = MaterialTheme.typography

    val spacing: Spacing
        @Composable get() = LocalSpacing.current
}
