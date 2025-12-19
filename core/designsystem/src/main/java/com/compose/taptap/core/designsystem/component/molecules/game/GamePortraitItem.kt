package com.compose.taptap.core.designsystem.component.molecules.game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.model.App
import com.compose.taptap.core.designsystem.component.atoms.image.TapTapNetworkImage
import com.compose.taptap.core.model.Icon
import kotlinx.collections.immutable.persistentListOf

@Composable
fun GamePortraitItem(
    item: App, onGameClick: (String) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(84.dp)
            .clickable { onGameClick(item.id.toString()) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(TapTapTheme.spacing.semiSmall)
    ) {
        val isPreview = LocalInspectionMode.current
        val iconUrl = item.icon?.mediumUrl ?: item.icon?.smallUrl

        // Try to parse the URL as a resource ID for preview if we are in preview mode
        val placeholderPainter = if (isPreview) {
            val resId = iconUrl?.toIntOrNull()
            if (resId != null) painterResource(resId) else null
        } else null

        TapTapNetworkImage(
            imageUrl = item.icon?.mediumUrl ?: item.icon?.smallUrl ?: item.icon?.url,
            contentDescription = item.title,
            modifier = Modifier
                .size(TapTapTheme.spacing.xxxxLarge)
                .clip(TapTapShape.corners.medium),
            placeholder = placeholderPainter,
            contentScale = ContentScale.Crop
        )
        Text(
            text = item.title,
            color = TapTapTheme.colors.onSurface,
            style = TapTapTheme.typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview
private fun PreviewGamePortraitItem() {
    val games = persistentListOf(
        App(
            id = 135082,
            identifier = "com.supercell.brawlstars",
            title = "Brawl Stars",
            titleLabels = listOf("Global"),
            icon = Icon(
                url = R.drawable.brawl_stars_app_icon.toString(),
                mediumUrl = R.drawable.brawl_stars_app_icon.toString(),
                smallUrl = R.drawable.brawl_stars_app_icon.toString(),
                originalUrl = "",
                originalFormat = "",
                width = 0,
                height = 0,
                color = ""
            ),
            uri = null,
            canView = true,
            releasedTime = 1530072066,
            stat = null,
            banner = null,
            tags = null,
            log = null,
            eventLog = null,
            complaint = null,
            supportedPlatforms = null,
            itunesId = "1229016807",
            recText = "Brawliday Opening, new game modes and skins await you!",
            videoResource = null
        ), App(
            id = 214547,
            identifier = "com.nebulajoy.act.dmcpoc",
            title = "Devil May Cry: Peak of Combat",
            titleLabels = listOf("Global"),
            icon = Icon(
                url = R.drawable.devil_may_cry_app_icon.toString(),
                mediumUrl = R.drawable.devil_may_cry_app_icon.toString(),
                smallUrl = R.drawable.devil_may_cry_app_icon.toString(),
                originalUrl = "",
                originalFormat = "",
                width = 0,
                height = 0,
                color = ""
            ),
            uri = null,
            canView = true,
            releasedTime = 1619766431,
            stat = null,
            banner = null,
            tags = null,
            log = null,
            eventLog = null,
            complaint = null,
            supportedPlatforms = null,
            itunesId = "6449589065",
            recText = "Only DMC Licensed on Mobile",
            videoResource = null
        ), App(
            id = 178546,
            identifier = "air.com.ubisoft.brawl.halla.platform.fighting.action.pvp",
            title = "Brawlhalla",
            titleLabels = emptyList(),
            icon = Icon(
                url = R.drawable.brawl_halla_app_icon.toString(),
                mediumUrl = R.drawable.brawl_halla_app_icon.toString(),
                smallUrl = R.drawable.brawl_halla_app_icon.toString(),
                originalUrl = "",
                originalFormat = "",
                width = 0,
                height = 0,
                color = ""
            ),
            uri = null,
            canView = true,
            releasedTime = 1596165246,
            stat = null,
            banner = null,
            tags = null,
            log = null,
            eventLog = null,
            complaint = null,
            supportedPlatforms = null,
            itunesId = "1491520571",
            recText = "Friends from Kung Fu Panda have joined Brawl!",
            videoResource = null
        )
    )
    GamePortraitItem(
        item = games[0], onGameClick = {})
}
