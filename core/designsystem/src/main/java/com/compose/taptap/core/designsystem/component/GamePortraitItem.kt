package com.compose.taptap.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.compose.taptap.core.designsystem.theme.PPNeu
import com.compose.taptap.core.model.App

@Composable
fun GamePortraitItem(
    item: App, onGameClick: (String) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(84.dp)
            .clickable { onGameClick(item.id.toString()) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        val isPreview = LocalInspectionMode.current
        val iconUrl = item.icon?.mediumUrl ?: item.icon?.smallUrl


        if (isPreview) {
            // Try to parse the URL as a resource ID for preview
            val resId = iconUrl?.toIntOrNull()
            if (resId != null) {
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(resId),
                    contentDescription = item.title,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            } else {
                // Fallback for preview if not a resource ID
                AsyncImage(
                    model = iconUrl,
                    contentDescription = item.title,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            AsyncImage(
                model = iconUrl,
                contentDescription = item.title,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(16.dp)),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = item.title,
            color = White,
            fontFamily = PPNeu,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}
