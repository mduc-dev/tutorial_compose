package com.compose.taptap.core.designsystem.util

import androidx.compose.ui.graphics.painter.Painter
import com.compose.taptap.core.navigation.TapTapScreen

data class BottomTab(
    val title: String,
    val icon: Painter? = null,
    val selectedIcon: Painter? = null,
    val route: TapTapScreen,
)
