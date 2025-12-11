package com.compose.taptap.core.designsystem.component.molecules.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapTapNotificationBell(
    unreadCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.home_notification_ic),
            contentDescription = "Notification",
            tint = TapTapTheme.colors.onSurface,
            modifier = Modifier.size(24.dp)
        )

        if (unreadCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-4).dp, y = 4.dp)
                    .height(12.dp).width(16.dp)
                    .clip(CircleShape)
                    .background(TapTapTheme.colors.error),
                contentAlignment = Alignment.Center
            ) {
                TapTapText(
                    text = if (unreadCount > 99) "99+" else unreadCount.toString(),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    style = TapTapTheme.typography.labelSmall,
                    color = TapTapTheme.colors.onError
                )
            }
        }
    }
}

@Preview
@Composable
private fun TapTapNotificationBellPreview() {
    TapTapTheme {
        TapTapNotificationBell(
            unreadCount = 5,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun TapTapNotificationBellNoBadgePreview() {
    TapTapTheme {
        TapTapNotificationBell(
            unreadCount = 0,
            onClick = {}
        )
    }
}
