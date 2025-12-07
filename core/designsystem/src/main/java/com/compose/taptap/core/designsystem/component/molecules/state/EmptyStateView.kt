package com.compose.taptap.core.designsystem.component.molecules.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun EmptyStateView(
    modifier: Modifier = Modifier,
    textNull: String = "Emptier than the void",
    subTextNull: String?,
    painterResourceName: Int? = R.drawable.sad_icon_right,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = painterResourceName!!),
                contentDescription = null,
            )
            Text(
                text = textNull,
                modifier = modifier.padding(vertical = TapTapTheme.spacing.small),
                style = TapTapTheme.typography.titleMedium,
                color = TapTapTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )
            if (subTextNull != null) {
                Text(
                    text = subTextNull,
                    style = TapTapTheme.typography.bodyMedium,
                    color = TapTapTheme.colors.onSurface,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
@Preview(apiLevel = 36, showBackground = true, backgroundColor = 0xFF1A1A1A, showSystemUi = true)
fun PreviewNoExistData() {
    EmptyStateView(
        subTextNull = "Write a post to start your profile’s never-ending journey",
        painterResourceName = R.drawable.confuse_icon,
        modifier = Modifier
    )
}
