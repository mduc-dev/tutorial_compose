package com.compose.taptap.ui.launcher.game_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.taptap.ui.theme.BlackF16

@Composable
fun GameDetail() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BlackF16)
            .statusBarsPadding()
    ) {
        Text("Game detail")
    }
}