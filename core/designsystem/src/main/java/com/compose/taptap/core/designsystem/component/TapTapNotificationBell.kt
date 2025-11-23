package com.compose.taptap.core.designsystem.component

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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.theme.PPNeu
import com.compose.taptap.core.designsystem.theme.V3CommonPrimaryRed

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
                    .width(16.dp),
                containerColor = V3CommonPrimaryRed,
                contentColor = White,
            ) {

                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                        .size(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = unreadCount.coerceAtMost(99).toString(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = PPNeu,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier.offset(x = 0.dp, y = -(1.5).dp)
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
            tint = White,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onClick)
        )
    }
}
