package com.example.kotlin_compose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import com.example.kotlin_compose.R

// Set of Material typography styles to start with
val Typography =
    Typography(
        bodyLarge =
        TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
        /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
         */
    )

// Set custom font
val PPNeu = FontFamily(
    Font(R.font.pp_neue_montreal_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pp_neue_montreal_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pp_neue_montreal_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.pp_neue_montreal_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pp_neue_montreal_bolditalic, FontWeight.Bold, FontStyle.Normal)
)