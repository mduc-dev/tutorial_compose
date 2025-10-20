package com.compose.taptap.core.data.model

import androidx.compose.ui.graphics.painter.Painter
import com.compose.taptap.core.navigation.TapTapScreen
/**
 * Created by duc on 20/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

data class BottomTab(
    val title: String,
    val icon: Painter,
    val selectedIcon: Painter,
    val route: TapTapScreen,
    val hasBadge: Boolean? = false,
    val badgeCount: Number? = null
)