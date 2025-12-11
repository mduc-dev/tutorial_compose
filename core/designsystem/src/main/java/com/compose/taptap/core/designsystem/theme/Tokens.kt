package com.compose.taptap.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class TapTapColorTokens(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val error: Color,
    val onError: Color,
    val divider: Color,
    val outline: Color,
    val surfaceContainerHighest: Color,
    val onSurfaceVariant: Color,
    val scrim: Color,
    val textGray: Color,
    val textGray40: Color
)

object TapTapTokens {
    val lightColors = TapTapColorTokens(
        primary = ColorPrimary,
        onPrimary = WhitePrimary,
        secondary = ColorAccent,
        onSecondary = WhitePrimary,
        background = BackgroundMaterialLight,
        onBackground = BlackPrimary,
        surface = WhitePrimary,
        onSurface = BlackPrimary,
        error = ErrorRed,
        onError = WhitePrimary,
        divider = IntlCcDivider,
        outline = IntlV2Grey60,
        surfaceContainerHighest = IntlV2Grey20,
        onSurfaceVariant = IntlV2Grey60,
        scrim = BlackF16,
        textGray = IntlCc999999,
        textGray40 = IntlV2Grey40
    )

    val darkColors = TapTapColorTokens(
        primary = GreenPrimary,
        onPrimary = WhitePrimary,
        secondary = GreenPrimary,
        onSecondary = WhitePrimary,
        background = BlackF16,
        onBackground = WhitePrimary,
        surface = Black20,
        onSurface = WhitePrimary,
        error = ErrorRed,
        onError = WhitePrimary,
        divider = IntlCcDivider,
        outline = IntlV2Grey60,
        surfaceContainerHighest = IntlV2Grey20,
        onSurfaceVariant = IntlV2Grey60,
        scrim = BlackF16,
        textGray = IntlCc999999,
        textGray40 = IntlV2Grey40
    )
}
