package com.compose.taptap.core.designsystem.component.atoms.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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


@Preview
@Composable
fun PreviewTapTapVerticalSpacer() {
    TapTapTheme(darkTheme = true, dynamicColor = false) {
        TapTapVerticalSpacer(height = TapTapTheme.spacing.medium)
    }
}

@Preview
@Composable
fun PreviewTapTapHorizontalSpacer() {
    TapTapTheme(darkTheme = true, dynamicColor = false) {
        TapTapHorizontalSpacer(width = TapTapTheme.spacing.medium)
    }
}