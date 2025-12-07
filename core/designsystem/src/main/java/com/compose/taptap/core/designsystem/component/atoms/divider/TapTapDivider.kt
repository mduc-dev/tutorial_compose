package com.compose.taptap.core.designsystem.component.atoms.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapTapDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = TapTapTheme.spacing.xSmall / 4, // Default fine line
    color: Color = TapTapTheme.colors.onSurface.copy(alpha = 0.12f)
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}
