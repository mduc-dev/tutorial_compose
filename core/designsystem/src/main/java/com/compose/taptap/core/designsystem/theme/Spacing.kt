package com.compose.taptap.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val xSmall: Dp = 2.dp,
    val tiny: Dp = 4.dp,
    val semiSmall: Dp = 6.dp,
    val small: Dp = 8.dp,
    val mediumSmall: Dp = 10.dp,
    val medium: Dp = 12.dp,
    val mediumLarge: Dp = 16.dp,
    val large: Dp = 24.dp,
    val xLarge: Dp = 32.dp,
    val xxLarge: Dp = 40.dp,
    val gutter: Dp = 20.dp,
    val iconButton: Dp = 12.dp,
    val cardPadding: Dp = 20.dp,
    val sectionSpacing: Dp = 28.dp,
)

internal val LocalSpacing = staticCompositionLocalOf { Spacing() }
