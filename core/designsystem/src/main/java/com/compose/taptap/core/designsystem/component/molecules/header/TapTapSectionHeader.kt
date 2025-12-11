package com.compose.taptap.core.designsystem.component.molecules.header

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.image.TapTapNetworkImage
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.model.User

@Composable
fun TapTapSectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    publishingUser: User,
    onMoreClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = TapTapTheme.spacing.mediumLarge)
            .clickable(onClick = onMoreClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(TapTapTheme.spacing.small)
        ) {
            Text(
                text = title,
                style = TapTapTheme.typography.titleLarge.copy(fontSize = 18.sp),
                color = TapTapTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            publishingUser.let { it ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TapTapNetworkImage(
                        imageUrl = publishingUser.avatar,
                        contentDescription = publishingUser.name,
                        modifier = Modifier
                            .size(TapTapTheme.spacing.large)
                            .clip(TapTapShape.corners.circle),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = it.name,
                        color = TapTapTheme.colors.onSurface,
                        style = TapTapTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

//dong tam
//        Icon(
//            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//            contentDescription = "More",
//            tint = TapTapTheme.colors.onSurface,
//            modifier = Modifier.size(32.dp)
//        )
    }
}

@Preview
@Composable
private fun PreviewTapTapSectionHeader() {
    TapTapSectionHeader(
        title = "The best games to relieve true medieval warfare", publishingUser = User(
            avatar = R.drawable.publisher_avatar.toString(),
            name = "T-CY",
            id = 12123123,
            mediumAvatar = "",
            gender = "",
            store = "",
            intro = "",
            isCertified = true,
            isAnonymous = true,
            isBan = false,
            isDeactivated = false,
        ), onMoreClick = {})
}
