package com.compose.taptap.ui.launcher.game_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun GameDetail() {
    val spacing = TapTapTheme.spacing
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = TapTapTheme.colors.background)
            .statusBarsPadding()
            .padding(spacing.mediumLarge)
    ) {
        Text(
            text = "Game detail",
            style = TapTapTheme.typography.headlineSmall,
            color = TapTapTheme.colors.onBackground
        )
    }
}
