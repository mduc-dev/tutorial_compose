package com.compose.taptap.core.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapTapPreviewTheme(
    content: @Composable () -> Unit
) {
    TapTapTheme {
        content()
    }
}
