package com.compose.taptap.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.compose.taptap.core.designsystem.R

private val PPNeu = FontFamily(
    Font(R.font.pp_neue_montreal_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pp_neue_montreal_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pp_neue_montreal_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.pp_neue_montreal_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pp_neue_montreal_bolditalic, FontWeight.Bold, FontStyle.Italic),
)

private fun TextStyle.copyWith(
    weight: FontWeight,
    size: Int,
    lineHeight: Int,
    letterSpacing: Float = 0f,
): TextStyle =
    copy(
        fontFamily = PPNeu,
        fontWeight = weight,
        fontSize = size.sp,
        lineHeight = lineHeight.sp,
        letterSpacing = letterSpacing.sp,
    )

val TapTapTypography = Typography(
    displayLarge = TextStyle.Default.copyWith(FontWeight.Normal, 57, 64),
    displayMedium = TextStyle.Default.copyWith(FontWeight.Normal, 45, 52),
    displaySmall = TextStyle.Default.copyWith(FontWeight.Normal, 36, 44),
    headlineLarge = TextStyle.Default.copyWith(FontWeight.Medium, 32, 40),
    headlineMedium = TextStyle.Default.copyWith(FontWeight.Medium, 28, 36),
    headlineSmall = TextStyle.Default.copyWith(FontWeight.Medium, 24, 32),
    titleLarge = TextStyle.Default.copyWith(FontWeight.Medium, 22, 28),
    titleMedium = TextStyle.Default.copyWith(FontWeight.Medium, 16, 24),
    titleSmall = TextStyle.Default.copyWith(FontWeight.Medium, 14, 20),
    bodyLarge = TextStyle.Default.copyWith(FontWeight.Normal, 16, 24, letterSpacing = 0.5f),
    bodyMedium = TextStyle.Default.copyWith(FontWeight.Normal, 14, 20, letterSpacing = 0.25f),
    bodySmall = TextStyle.Default.copyWith(FontWeight.Normal, 12, 16, letterSpacing = 0.4f),
    labelLarge = TextStyle.Default.copyWith(FontWeight.Medium, 14, 20),
    labelMedium = TextStyle.Default.copyWith(FontWeight.Medium, 12, 16),
    labelSmall = TextStyle.Default.copyWith(FontWeight.Medium, 11, 16),
)
