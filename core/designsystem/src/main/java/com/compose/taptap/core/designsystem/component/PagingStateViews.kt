package com.compose.taptap.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun PagingErrorState(
    message: String?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TapTapTheme.colors.background)
            .padding(TapTapTheme.spacing.large),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message ?: "Something went wrong.",
            style = TapTapTheme.typography.bodyLarge,
            color = TapTapTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(TapTapTheme.spacing.large))
        Text(
            text = "Tap to retry",
            style = TapTapTheme.typography.labelLarge,
            color = TapTapTheme.colors.primary,
            modifier = Modifier
                .clip(TapTapShape.corners.pill)
                .clickable { onRetry() }
                .padding(horizontal = TapTapTheme.spacing.xxLarge, vertical = TapTapTheme.spacing.small)
        )
    }
}

@Composable
fun PagingAppendErrorFooter(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Couldn't load more games.",
            style = TapTapTheme.typography.bodySmall,
            color = TapTapTheme.colors.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = "Retry",
            style = TapTapTheme.typography.labelLarge,
            color = TapTapTheme.colors.primary,
            modifier = Modifier
                .clip(TapTapShape.corners.pill)
                .clickable { onRetry() }
                .padding(horizontal = TapTapTheme.spacing.large, vertical = TapTapTheme.spacing.small)
        )
    }
}

@Composable
fun AppendLoadingIndicator(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.wrapContentSize(Alignment.Center),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            color = TapTapTheme.colors.primary,
            strokeWidth = 2.dp,
            modifier = Modifier.size(24.dp)
        )
    }
}
