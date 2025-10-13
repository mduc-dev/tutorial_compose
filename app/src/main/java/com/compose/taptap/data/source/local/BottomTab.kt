package com.compose.taptap.data.source.local

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigation(
    val title: String,
    val icon: Painter,
    val selectedIcon: Painter,
    val route: String,
    val hasBadge: Boolean? = false,
    val badgeCount: Number? = null
)
