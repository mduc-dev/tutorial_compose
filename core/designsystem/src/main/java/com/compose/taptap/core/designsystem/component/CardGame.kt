package com.compose.taptap.core.designsystem.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.DisableParentPagerSwipeConnection
import com.compose.taptap.core.model.App
import com.compose.taptap.core.model.DailiesItem
import com.compose.taptap.core.model.Icon
import com.compose.taptap.core.model.ListGameItem
import com.compose.taptap.core.model.User
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
        override val id: String, val title: String, val games: ImmutableList<App>, val user: User
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
            games = category.list.toImmutableList(),
            user = category.user
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
                    color = TapTapTheme.colors.onSurface.copy(alpha = 0.6f),
                    style = TapTapTheme.typography.labelSmall
                )
            }
        } else {
            Text(
                text = if (!isLast) "$item •" else item,
                color = TapTapTheme.colors.onSurface.copy(alpha = 0.6f),
                style = TapTapTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun PlatformIcon(platform: String) {
    val iconSize = TapTapTheme.spacing.iconButton
    when (platform.lowercase()) {
        "android" -> Icon(
            painterResource(R.drawable.ico_12_platform_android),
            "Android",
            Modifier.size(iconSize),
            tint = Color.Unspecified
        )

        "ios" -> Icon(
            painterResource(R.drawable.ico_12_platform_ios),
            "iOS",
            Modifier.size(iconSize),
            tint = Color.Unspecified
        )

        "pc" -> Icon(
            painterResource(R.drawable.ico_12_platform_pc),
            "Pc",
            Modifier.size(iconSize),
            tint = Color.Unspecified
        )

        "playstation" -> Icon(
            painterResource(R.drawable.ico_12_platform_ps),
            "Ps",
            Modifier.size(iconSize),
            tint = Color.Unspecified
        )

        "ns" -> Icon(
            painterResource(R.drawable.ico_12_platform_switch),
            "Ns",
            Modifier.size(iconSize),
            tint = Color.Unspecified
        )

        "xbox" -> Icon(
            painterResource(R.drawable.ico_12_platform_xbox),
            "Xbox",
            Modifier.size(iconSize),
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
            uiState = uiState, modifier = modifier, onClick = onClick
        )

        is GameCardUiState.Featured -> FeaturedGameCard(
            uiState = uiState, modifier = modifier, onClick = onClick
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
                    .padding(horizontal = TapTapTheme.spacing.mediumLarge),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(TapTapTheme.spacing.mediumLarge)
            ) {
                Box(
                    modifier = Modifier
                        .size(TapTapTheme.spacing.xxLarge)
                        .clip(TapTapShape.corners.medium), contentAlignment = Alignment.Center
                ) {
                    NetworkImage(
                        imageUrl = uiState.iconUrl,
                        contentDescription = uiState.title,
                        modifier = Modifier.fillMaxSize(),
                        placeholder = if (isPreview) painterResource(R.drawable.justice_app_icon) else placeholder
                    )
                }

                Column {
                    Text(
                        text = uiState.title,
                        style = TapTapTheme.typography.titleMedium,
                        color = TapTapTheme.colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(TapTapTheme.spacing.xSmall)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.review_star_selected_gray),
                            contentDescription = "review_star",
                            tint = TapTapTheme.colors.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier
                                .size(TapTapTheme.spacing.iconButton)
                                .offset(y = (-TapTapTheme.spacing.xSmall / 2))
                        )
                        Text(
                            text = uiState.rating ?: "--",
                            color = TapTapTheme.colors.onSurface.copy(alpha = 0.6f),
                            style = TapTapTheme.typography.bodySmall,
                            lineHeight = TapTapTheme.typography.bodySmall.lineHeight
                        )
                        TagLine(uiState.tagLineItems)
                    }
                }
            }

            OutlinedButton(
                onClick = clickAction,
                border = BorderStroke(
                    width = TapTapTheme.spacing.xSmall, color = TapTapTheme.colors.primary
                ),
                contentPadding = PaddingValues(
                    horizontal = TapTapTheme.spacing.mediumLarge,
                    vertical = TapTapTheme.spacing.xSmall
                ),
                modifier = Modifier
                    .height(TapTapTheme.spacing.xLarge)
                    .padding(end = TapTapTheme.spacing.mediumLarge)
            ) {
                Text(
                    "Get",
                    color = TapTapTheme.colors.primary,
                    style = TapTapTheme.typography.labelLarge
                )
            }
        }
        val imageModifier = Modifier
            .padding(
                top = TapTapTheme.spacing.small,
                start = TapTapTheme.spacing.mediumLarge,
                end = TapTapTheme.spacing.mediumLarge
            )
            .fillMaxWidth()
            .aspectRatio(16f / 9f)

        NetworkImage(
            imageUrl = uiState.bannerUrl,
            contentDescription = uiState.bannerUrl,
            modifier = imageModifier,
            placeholder = if (isPreview) painterResource(R.drawable.justice_cover) else placeholder,
            contentScale = ContentScale.Crop
        )

        if (!uiState.recReason.isNullOrBlank()) {
            Text(
                text = uiState.recReason,
                style = TapTapTheme.typography.bodyMedium,
                color = TapTapTheme.colors.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(
                    top = TapTapTheme.spacing.small, start = TapTapTheme.spacing.mediumLarge
                )
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = TapTapTheme.spacing.small),
            thickness = TapTapTheme.spacing.xSmall / 4,
            color = TapTapTheme.colors.onSurface.copy(alpha = 0.12f)
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
            containerColor = TapTapTheme.colors.surface, contentColor = TapTapTheme.colors.onSurface
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            NetworkImage(
                imageUrl = uiState.coverUrl,
                contentDescription = uiState.coverUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TapTapTheme.spacing.xxLarge * 4 + TapTapTheme.spacing.mediumLarge),
                placeholder = if (isPreview) painterResource(R.drawable.horizon_cover) else placeholder
            )
            Row {
                Text(
                    text = uiState.title,
                    style = TapTapTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = uiState.rating, style = TapTapTheme.typography.bodyMedium
                )
            }
            Text(
                text = uiState.description,
                style = TapTapTheme.typography.bodyMedium,
                color = TapTapTheme.colors.onSurface.copy(alpha = 0.7f),
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = TapTapTheme.spacing.small),
                thickness = TapTapTheme.spacing.xSmall / 4,
                color = TapTapTheme.colors.onSurface.copy(alpha = 0.12f)
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
            .padding(vertical = TapTapTheme.spacing.mediumLarge),
        verticalArrangement = Arrangement.spacedBy(TapTapTheme.spacing.large)
    ) {
        SectionHeader(
            title = uiState.title,
            publishingUser = uiState.user,
            onMoreClick = { onCategoryClick(uiState.id) })

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(DisableParentPagerSwipeConnection),
            contentPadding = PaddingValues(horizontal = TapTapTheme.spacing.mediumLarge),
            horizontalArrangement = Arrangement.spacedBy(TapTapTheme.spacing.medium)
        ) {
            items(uiState.games.size) { index ->
                GamePortraitItem(
                    item = uiState.games[index], onGameClick = onGameClick
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
    TapTapTheme(darkTheme = true, dynamicColor = false) {
        CardGame(
            uiState = GameCardUiState.Category(
                id = "33854088",
                title = "Top 15 best hack and slash games for phones and tablets",
                games = games,
                user = User(
                    id = 123,
                    name = "TapTap Editor",
                    avatar = "",
                    mediumAvatar = "",
                    gender = "",
                    store = "",
                    intro = "",
                    isCertified = true,
                    isAnonymous = false,
                    isBan = false,
                    isDeactivated = false
                )
            ), onClick = {})
    }
}

@Preview
@Composable
private fun PreviewStandardGameCard() {
    TapTapTheme(darkTheme = true, dynamicColor = false) {
        CardGame(
            uiState = GameCardUiState.Standard(
                id = "33854088",
                title = "SWORD OF JUSTICE",
                iconUrl = "https://img.tapimg.net/market/images/54eaaa51c6b914a74f048f96f505e829.png/appicon_s?t=1",
                bannerUrl = "https://img.tapimg.net/market/images/44ac18c469939641cedf07fd89a7e98c.jpg?imageView2/0/w/720/h/405/format/jpg/interlace/1/ignore-error/1&t=1",
                rating = "7.6",
                recReason = "Sword of Justice champions true fair play by eliminating all pay-to-win mechanics—victory comes from skill, not spending.",
                tagLineItems = persistentListOf("RPG", "Action", "Editors' Choice")
            ), onClick = {})
    }
}

@Preview
@Composable
private fun PreviewFeaturedGameCard() {
    TapTapTheme(darkTheme = true, dynamicColor = false) {
        CardGame(
            uiState = GameCardUiState.Featured(
                id = "app:33910299",
                title = "Horizon Steel Frontiers",
                coverUrl = "https://img.tapimg.net/market/images/9b5afd71100f824d8f118f1bb1ef78e7.png?imageView2/0/w/720/h/405/format/jpg/interlace/1/ignore-error/1&t=1",
                description = "An Open-world MMORPG set in the world of the Horizon series! Pre-register now!",
                rating = "9.6"
            ), onClick = {})
    }
}
