package com.compose.taptap.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun FailureScreen(
    isLoading: Boolean,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Oh no, something went wrong!",
            style = TapTapTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = TapTapTheme.spacing.large)
        )
        Button(onClick = onRetry) {
            AnimatedVisibility(visible = isLoading) {
                CircularProgressIndicator(
                    color = LocalContentColor.current,
                    modifier = Modifier
                        .padding(end = TapTapTheme.spacing.small)
                        .size(14.dp)
                )
            }
            Text("Try again", style = TapTapTheme.typography.bodyLarge)
        }
    }
}
