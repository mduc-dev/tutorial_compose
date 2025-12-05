package com.compose.taptap.core.designsystem.util

import androidx.compose.ui.graphics.painter.Painter
import com.compose.taptap.core.navigation.TapTapScreen

data class BottomTab(
    val title: String,
    val icon: Painter,
    val selectedIcon: Painter,
    val route: TapTapScreen,
    val hasBadge: Boolean? = false,
    val badgeCount: Number? = null,
)
