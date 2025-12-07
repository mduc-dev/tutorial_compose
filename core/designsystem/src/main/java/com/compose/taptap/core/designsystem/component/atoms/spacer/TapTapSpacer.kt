package com.compose.taptap.core.designsystem.component.atoms.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapTapVerticalSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun TapTapHorizontalSpacer(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}
