package com.compose.taptap.feature.tavern

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText

@Composable
fun Tavern() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TapTapText(
                text = "Tavern",
                color = com.compose.taptap.core.designsystem.theme.TapTapTheme.colors.onBackground
            )
        }
    }
}
