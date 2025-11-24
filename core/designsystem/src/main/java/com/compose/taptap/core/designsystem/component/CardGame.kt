package com.compose.taptap.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.theme.BlackF16
import com.compose.taptap.core.designsystem.theme.GreenPrimary
import com.compose.taptap.core.designsystem.theme.IntlV2Grey20
import com.compose.taptap.core.designsystem.theme.IntlV2Grey40
import com.compose.taptap.core.designsystem.theme.PPNeu
import com.compose.taptap.core.designsystem.theme.WhitePrimary
import com.compose.taptap.core.designsystem.util.DisableParentPagerSwipeConnection
import com.compose.taptap.core.designsystem.util.nonScaledSp
import com.compose.taptap.core.model.App
import com.compose.taptap.core.model.DailiesItem
import com.compose.taptap.core.model.Icon
import com.compose.taptap.core.model.ListGameItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList


/**
 * Sealed interface representing the UI state for a game card.
 * This allows a single CardGame composable to handle different card types
 * in a type-safe manner.
 */
@Immutable
sealed interface GameCardUiState {
    val id: String

    /**
     * Standard game card with banner, icon, and metadata.
     */
    @Immutable
    data class Standard(
        override val id: String,
        val title: String,
        val iconUrl: String?,
        val bannerUrl: String?,
        val rating: String?,
        val recReason: String?,
        val tagLineItems: ImmutableList<String>,
    ) : GameCardUiState

    /**
     * Featured daily game card with cover image and description.
     */
    @Immutable
    data class Featured(
        override val id: String,
        val title: String,
        val coverUrl: String?,
        val description: String,
        val rating: String
    ) : GameCardUiState

    @Immutable
    data class Category(
        override val id: String,
        val title: String,
        val games: ImmutableList<App>
    ) : GameCardUiState
}

/**
 * Extension function to convert ListGameItem to GameCardUiState
 */
fun ListGameItem.toCardUiState(): GameCardUiState {
    val category = this.category
    if (category != null) {
        return GameCardUiState.Category(
            id = category.id.toString(),
            title = category.title,
            games = category.list.toImmutableList()
        )
    }

    val app = this.app
    if (app != null) {
        val platforms = app.supportedPlatforms?.map { it.key }.orEmpty()
        val tags = app.tags?.map { it.value }.orEmpty().filter { it.isNotBlank() }.take(3)
        val tagLineItems = when {
            tags.size >= 3 -> tags
            tags.isNotEmpty() -> tags + platforms
            platforms.isNotEmpty() -> platforms
            else -> emptyList()
        }

        return GameCardUiState.Standard(
            id = app.id.toString(),
            title = app.title,
            iconUrl = app.icon?.smallUrl,
            bannerUrl = app.banner?.mediumUrl,
            rating = app.stat?.rating?.score?.takeIf { it.isNotBlank() },
            recReason = this.recReason?.text,
            tagLineItems = tagLineItems.toImmutableList()
        )
    }

    error("ListGameItem must have either an app or a category")
}

/**
 * Extension function to convert DailiesItem to GameCardUiState.Featured
 */
fun DailiesItem.toCardUiState(): GameCardUiState.Featured {
    return GameCardUiState.Featured(
        id = this.identification,
        title = this.app?.title ?: this.title ?: "Unknown Title",
        coverUrl = this.cover?.url,
        description = this.description ?: "",
        rating = this.app?.stat?.rating?.score ?: ""
    )
}

@Composable
fun TagLine(items: ImmutableList<String>?) {
    if (items.isNullOrEmpty()) return

    items.forEachIndexed { index, item ->
        val isPlatform = isPlatform(item)
        val isLast = index == items.lastIndex

        if (isPlatform) {
            // icon
            PlatformIcon(item)

            val next = items.getOrNull(index + 1)
            if (next != null && !isPlatform(next)) {
                Text(
                    text = " •",
                    color = IntlV2Grey40,
                    fontSize = 12.sp.nonScaledSp,
                    fontFamily = PPNeu,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            Text(
                text = if (!isLast) "$item •" else item,
                color = IntlV2Grey40,
                fontSize = 12.sp.nonScaledSp,
                fontFamily = PPNeu,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 12.sp
            )
        }
    }
}

@Composable
fun PlatformIcon(platform: String) {
    when (platform.lowercase()) {
        "android" -> Icon(
            painterResource(R.drawable.ico_12_platform_android),
            "Android",
            Modifier.size(12.dp),
            tint = Color.Unspecified
        )

        "ios" -> Icon(
            painterResource(R.drawable.ico_12_platform_ios),
            "iOS",
            Modifier.size(12.dp),
            tint = Color.Unspecified
        )

        "pc" -> Icon(
            painterResource(R.drawable.ico_12_platform_pc),
            "Pc",
            Modifier.size(12.dp),
            tint = Color.Unspecified
        )

        "playstation" -> Icon(
            painterResource(R.drawable.ico_12_platform_ps),
            "Ps",
            Modifier.size(12.dp),
            tint = Color.Unspecified
        )

        "ns" -> Icon(
            painterResource(R.drawable.ico_12_platform_switch),
            "Ns",
            Modifier.size(12.dp),
            tint = Color.Unspecified
        )

        "xbox" -> Icon(
            painterResource(R.drawable.ico_12_platform_xbox),
            "Xbox",
            Modifier.size(12.dp),
            tint = Color.Unspecified
        )
    }
}

fun isPlatform(item: String): Boolean {
    return item.lowercase() in listOf("android", "ios", "pc", "playstation", "ns", "xbox")
}

/**
 * Unified CardGame composable that handles both standard and featured game cards.
 * Uses sealed interface GameCardUiState for type-safe rendering.
 */
@Composable
fun CardGame(
    uiState: GameCardUiState,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit = {},
) {
    when (uiState) {
        is GameCardUiState.Standard -> StandardGameCard(
            uiState = uiState,
            modifier = modifier,
            onClick = onClick
        )

        is GameCardUiState.Featured -> FeaturedGameCard(
            uiState = uiState,
            modifier = modifier,
            onClick = onClick
        )

        is GameCardUiState.Category -> CategoryGameCard(
            uiState = uiState,
            modifier = modifier,
            onGameClick = onClick,
            onCategoryClick = onCategoryClick
        )
    }
}

@Composable
private fun StandardGameCard(
    uiState: GameCardUiState.Standard,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    val placeholder = remember { ColorPainter(Color.DarkGray) }
    val clickAction = remember(uiState.id, onClick) { { onClick(uiState.id) } }
    val isPreview = LocalInspectionMode.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = clickAction)
    ) {
        Row(
            modifier = modifier, verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (isPreview) {
                        Image(
                            painter = painterResource(R.drawable.justice_app_icon),
                            contentDescription = uiState.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        AsyncImage(
                            model = uiState.iconUrl,
                            contentDescription = uiState.title,
                            modifier = Modifier.fillMaxSize(),
                            placeholder = placeholder
                        )
                    }
                }

                Column {
                    Text(
                        text = uiState.title,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = PPNeu,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp.nonScaledSp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.review_star_selected_gray),
                            contentDescription = "review_star",
                            tint = IntlV2Grey40,
                            modifier = Modifier
                                .size(10.dp)
                                .offset(y = (-1).dp)
                        )
                        Text(
                            text = uiState.rating ?: "--",
                            color = IntlV2Grey40,
                            fontSize = 12.sp.nonScaledSp,
                            fontFamily = PPNeu,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 12.sp
                        )
                        TagLine(uiState.tagLineItems)
                    }
                }
            }

            OutlinedButton(
                onClick = clickAction,
                border = BorderStroke(1.5.dp, GreenPrimary),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                modifier = Modifier
                    .height(32.dp)
                    .padding(end = 14.dp)
            ) {
                Text(
                    "Get",
                    color = GreenPrimary,
                    fontSize = 14.sp.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PPNeu
                )
            }
        }
        val imageModifier = Modifier
            .padding(top = 10.dp, start = 14.dp, end = 14.dp)
            .fillMaxWidth()
            .aspectRatio(16f / 9f)

        if (isPreview) {
            Image(
                painter = painterResource(R.drawable.justice_cover),
                contentDescription = uiState.bannerUrl,
                contentScale = ContentScale.Crop,
                modifier = imageModifier
            )
        } else {
            AsyncImage(
                model = uiState.bannerUrl,
                contentDescription = uiState.bannerUrl,
                modifier = imageModifier,
                placeholder = placeholder,
                contentScale = ContentScale.Crop
            )
        }

        if (!uiState.recReason.isNullOrBlank()) {
            Text(
                text = uiState.recReason,
                color = IntlV2Grey40,
                fontFamily = PPNeu,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp.nonScaledSp,
                lineHeight = 14.sp,
                modifier = Modifier.padding(top = 12.dp, start = 14.dp)
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp), thickness = 0.2.dp, color = IntlV2Grey20
        )
    }
}

@Composable
private fun FeaturedGameCard(
    uiState: GameCardUiState.Featured,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    val placeholder = remember { ColorPainter(Color.DarkGray) }
    val clickAction = remember(uiState.id, onClick) { { onClick(uiState.id) } }
    val isPreview = LocalInspectionMode.current

    Card(
        modifier = modifier.clickable(onClick = clickAction),
        colors = CardDefaults.cardColors(
            containerColor = BlackF16, contentColor = WhitePrimary
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isPreview) {
                Image(
                    painter = painterResource(R.drawable.horizon_cover),
                    contentDescription = uiState.coverUrl,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            } else {
                AsyncImage(
                    model = uiState.coverUrl,
                    contentDescription = uiState.coverUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    placeholder = placeholder
                )
            }
            Row {
                Text(
                    text = uiState.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = PPNeu,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp.nonScaledSp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(text = uiState.rating)
            }
            Text(
                text = uiState.description,
                fontFamily = PPNeu,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp.nonScaledSp,
                lineHeight = 14.sp,
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                thickness = 0.2.dp,
            )
        }
    }
}

@Composable
private fun CategoryGameCard(
    uiState: GameCardUiState.Category,
    modifier: Modifier = Modifier,
    onGameClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        SectionHeader(
            title = uiState.title,
            onMoreClick = { onCategoryClick(uiState.id) }
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(DisableParentPagerSwipeConnection),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.games.size) { index ->
                GamePortraitItem(
                    item = uiState.games[index],
                    onGameClick = onGameClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCategoryGameCard() {
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
        ),
        App(
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
        ),
        App(
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
    MaterialTheme {
        CardGame(
            uiState = GameCardUiState.Category(
                id = "33854088",
                title = "Top 15 best hack and slash games for phones and tablets",
                games = games
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewStandardGameCard() {
    MaterialTheme {
        CardGame(
            uiState = GameCardUiState.Standard(
                id = "33854088",
                title = "SWORD OF JUSTICE",
                iconUrl = "https://img.tapimg.net/market/images/54eaaa51c6b914a74f048f96f505e829.png/appicon_s?t=1",
                bannerUrl = "https://img.tapimg.net/market/images/44ac18c469939641cedf07fd89a7e98c.jpg?imageView2/0/w/720/h/405/format/jpg/interlace/1/ignore-error/1&t=1",
                rating = "7.6",
                recReason = "Sword of Justice champions true fair play by eliminating all pay-to-win mechanics—victory comes from skill, not spending.",
                tagLineItems = persistentListOf("RPG", "Action", "Editors' Choice")
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewFeaturedGameCard() {
    MaterialTheme {
        CardGame(
            uiState = GameCardUiState.Featured(
                id = "app:33910299",
                title = "Horizon Steel Frontiers",
                coverUrl = "https://img.tapimg.net/market/images/9b5afd71100f824d8f118f1bb1ef78e7.png?imageView2/0/w/720/h/405/format/jpg/interlace/1/ignore-error/1&t=1",
                description = "An Open-world MMORPG set in the world of the Horizon series! Pre-register now!",
                rating = "9.6"
            ),
            onClick = {}
        )
    }
}
