package com.compose.taptap.core.designsystem.util

import androidx.compose.runtime.Composable
import com.compose.taptap.core.navigation.TapTapScreen

data class BottomTab(
    val title: String,
    val route: TapTapScreen,
    val content: @Composable (selected: Boolean) -> Unit
)
