package com.compose.taptap.core.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.view.WindowCompat


val LocalTapTapColors = staticCompositionLocalOf<TapTapColorTokens> {
    error("No TapTapColors provided")
}

object TapTapTheme {
    @Composable
    operator fun invoke(
        darkTheme: Boolean = isSystemInDarkTheme(),
        dynamicColor: Boolean = false,
        content: @Composable () -> Unit,
    ) {
        val tapTapColors = if (darkTheme) TapTapTokens.darkColors else TapTapTokens.lightColors

        val colorScheme =
            when {
                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    val context = LocalContext.current
                    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                        context
                    )
                }

                darkTheme -> darkColorScheme(
                    primary = tapTapColors.primary,
                    onPrimary = tapTapColors.onPrimary,
                    secondary = tapTapColors.secondary,
                    onSecondary = tapTapColors.onSecondary,
                    background = tapTapColors.background,
                    onBackground = tapTapColors.onBackground,
                    surface = tapTapColors.surface,
                    onSurface = tapTapColors.onSurface,
                    error = tapTapColors.error,
                    onError = tapTapColors.onError,
                    // Map tokens to Material slots if needed, e.g. mapping divider to outlineVariant if acceptable,
                    // but since we have custom usage now, we don't strictly need to force it unless M3 components need it.
                    // We'll leave outlineVariant as default or map it if we want consistency.
                    // outlineVariant = tapTapColors.divider 
                )

                else -> lightColorScheme(
                    primary = tapTapColors.primary,
                    onPrimary = tapTapColors.onPrimary,
                    secondary = tapTapColors.secondary,
                    onSecondary = tapTapColors.onSecondary,
                    background = tapTapColors.background,
                    onBackground = tapTapColors.onBackground,
                    surface = tapTapColors.surface,
                    onSurface = tapTapColors.onSurface,
                    error = tapTapColors.error,
                    onError = tapTapColors.onError,
                )
            }
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                    !darkTheme
            }
        }

        CompositionLocalProvider(
            LocalTapTapColors provides tapTapColors,
            LocalSpacing provides Spacing(),
            LocalShapeTokens provides ShapeTokens(),
        ) {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = TapTapTypography,
                shapes = TapTapShapes,
                content = {
                    Box(
                        modifier = Modifier.semantics {
                            testTagsAsResourceId = true
                        }
                    ) {
                        content()
                    }
                }
            )
        }
    }

    val colors: TapTapColorTokens
        @Composable get() = LocalTapTapColors.current

    val typography: Typography
        @Composable get() = MaterialTheme.typography

    val spacing: Spacing
        @Composable get() = LocalSpacing.current
}
