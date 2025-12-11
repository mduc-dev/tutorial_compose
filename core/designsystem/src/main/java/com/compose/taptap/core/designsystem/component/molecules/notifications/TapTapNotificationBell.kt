package com.compose.taptap.core.designsystem.component.molecules.notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapTapNotificationBell(
    unreadCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BadgedBox(
        badge = {
        Badge(
            modifier = Modifier
                .offset(x = -(7).dp)
                .height(12.dp)
                .width(16.dp).align(Alignment.CenterEnd),
            containerColor = TapTapTheme.colors.error,
            contentColor = TapTapTheme.colors.onError,
        ) {

                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                        .size(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = unreadCount.coerceAtMost(99).toString(),
                        modifier = Modifier.offset(x = 0.dp, y = -(1.5).dp),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        style = TapTapTheme.typography.labelSmall,
                        color = TapTapTheme.colors.onError
                    )
                }
            }
        }, modifier = modifier
            .width(46.dp)
            .height(24.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.home_notification_ic),
            contentDescription = "Notification",
            tint = TapTapTheme.colors.onSurface,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onClick)
        )
    }
}
