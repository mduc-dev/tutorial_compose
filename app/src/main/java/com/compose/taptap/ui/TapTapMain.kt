package com.compose.taptap.ui

import androidx.compose.runtime.Composable
import com.compose.taptap.navigation.TapNavHost
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapTapMain() {
    TapTapTheme(darkTheme = true, dynamicColor = false) {
        TapNavHost()
    }
}
