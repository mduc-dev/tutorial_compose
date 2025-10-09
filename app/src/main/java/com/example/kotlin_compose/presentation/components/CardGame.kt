package com.example.kotlin_compose.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.kotlin_compose.R
import com.example.kotlin_compose.data.network.models.App
import com.example.kotlin_compose.data.network.models.Banner
import com.example.kotlin_compose.data.network.models.Cover
import com.example.kotlin_compose.data.network.models.DailiesItem
import com.example.kotlin_compose.data.network.models.Rating
import com.example.kotlin_compose.data.network.models.Stat
import com.example.kotlin_compose.data.network.models.Tag
import com.example.kotlin_compose.domain.models.Games
import com.example.kotlin_compose.presentation.screens.welcome.nonScaledSp
import com.example.kotlin_compose.ui.theme.BlackF16
import com.example.kotlin_compose.ui.theme.GreenPrimary
import com.example.kotlin_compose.ui.theme.IntlV2Grey20
import com.example.kotlin_compose.ui.theme.IntlV2Grey40
import com.example.kotlin_compose.ui.theme.Kotlin_composeTheme
import com.example.kotlin_compose.ui.theme.PPNeu

@Composable
fun TagLine(items: List<String>?) {
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

@Composable
fun CardGame(
    game: Games,
    modifier: Modifier = Modifier,
    onClick: (Games) -> Unit,
) {
    val currentGame = rememberUpdatedState(game)
    val clickAction = remember(onClick) { { onClick(currentGame.value) } }
    val placeholder = remember { ColorPainter(Color.DarkGray) }

    val app = game.app
    val bannerUrl = app?.banner?.mediumUrl
    val iconUrl = app?.icon?.smallUrl
    val rating = app?.stat?.rating?.score?.takeIf { it.isNotBlank() }
    val recReason = game.recReason?.text
    val platforms = app?.supportedPlatforms?.map { it.key }.orEmpty()
    val tags = app?.tags?.map { it.value }.orEmpty().filter { it.isNotBlank() }.take(3)
    val tagLineItems = remember(tags, platforms) {
        when {
            tags.size >= 3 -> tags
            tags.isNotEmpty() -> tags + platforms
            platforms.isNotEmpty() -> platforms
            else -> emptyList()
        }
    }

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
                    AsyncImage(
                        model = iconUrl,
                        contentDescription = app?.title,
                        modifier = Modifier.fillMaxSize(),
                        placeholder = ColorPainter(Color.DarkGray)
                    )
                }

                Column {
                    Text(
                        text = app?.title ?: stringResource(id = R.string.unknown_game),
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
                            text = rating ?: "--",
                            color = IntlV2Grey40,
                            fontSize = 12.sp.nonScaledSp,
                            fontFamily = PPNeu,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 12.sp
                        )
                        TagLine(tagLineItems)
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
        AsyncImage(
            model = bannerUrl,
            contentDescription = app?.banner?.mediumUrl,
            modifier = Modifier
                .fillMaxSize()
                .height(180.dp),
            placeholder = placeholder
        )

        if (!recReason.isNullOrBlank()) {
            Text(
                text = recReason,
                color = IntlV2Grey40,
                fontFamily = PPNeu,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp.nonScaledSp,
                lineHeight = 14.sp,
                modifier = Modifier.padding(top = 12.dp, start = 14.dp)
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp),
            thickness = 0.2.dp,
            color = IntlV2Grey20
        )
    }
}

//TODO: rewrite mapper to cast all data from type to 1 dto using
@Composable
fun CardGame(
    game: DailiesItem,
    modifier: Modifier = Modifier,
    onClick: (DailiesItem) -> Unit,
) {
    val currentGame = rememberUpdatedState(game)
    val clickAction = remember(onClick) { { onClick(currentGame.value) } }
    val placeholder = remember { ColorPainter(Color.DarkGray) }

    val app = game.app
    val title = app?.title ?: "Unknown Game"
    val coverUrl = game.cover?.mediumUrl
    val description = game.description ?: "Unknown description"

    val rating = app?.stat?.rating?.score ?: "0"
    val isPreview = LocalInspectionMode.current
    Card(
        modifier = modifier.clickable { onClick(game) },
        colors = CardDefaults.cardColors(containerColor = BlackF16),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = coverUrl,
                contentDescription = coverUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                placeholder = if (isPreview) painterResource(R.drawable.cover) else placeholder
            )
            Row {
                Text(
                    text = title,
//            color = colorResource(R.color.white),
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = PPNeu,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp.nonScaledSp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(text = rating)
            }
            Text(
                text = description,
//            color = colorResource(R.color.intl_v2_grey_40),
                fontFamily = PPNeu,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp.nonScaledSp,
                lineHeight = 14.sp,
            )
//        AsyncImage(
//            model = painterResource(R.drawable.logo),
//            contentDescription = coverUrl,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(32.dp)
//        )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                thickness = 0.2.dp,
//            color = colorResource(R.color.intl_v2_grey_20)
            )
        }
    }
}

@Preview
@Composable
fun CardGamePreview(
    @PreviewParameter(CardGamePreviewProvider::class) game: DailiesItem
) {
    Kotlin_composeTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CardGame(game = game, onClick = {})
        }
    }
}


class CardGamePreviewProvider : PreviewParameterProvider<DailiesItem> {
    override val values: Sequence<DailiesItem> = sequenceOf(
        DailiesItem(
            identification = "sample-001", type = "game", app = App(
                id = 1,
                identifier = "hero_game",
                title = "Hero Game",
                titleLabels = listOf("Featured", "Top Rated"),
                uri = "https://example.com/game/hero",
                canView = true,
                stat = Stat(
                    rating = Rating(
                        score = "4.8", max = 5, latestScore = "4.9", latestVersionScore = "4.7"
                    ),
                    voteInfo = null,
                    hitsTotal = 10000,
                    reviewCount = 1234,
                    fansCount = 5000,
                    userWantCount = 2000,
                    userPlayedCount = 3000,
                    userPlayingCount = 400,
                    reserveCount = 50
                ),
                banner = Banner(
                    url = "https://img.tapimg.net/market/images/FgNV-WKYmmNL_L5-XYSx1T4o32iG.png/appbanner",
                    mediumUrl = "https://img.tapimg.net/market/images/FgNV-WKYmmNL_L5-XYSx1T4o32iG.png/appbanner_m",
                    smallUrl = null,
                    originalUrl = null,
                    originalFormat = null,
                    width = 1080,
                    height = 540,
                    color = "#000000"
                ),
                tags = listOf(
                    Tag(id = 0, value = "Action", uri = "", webUrl = ""),
                    Tag(id = 1, value = "Multiplayer", uri = "", webUrl = ""),
                    Tag(id = 2, value = "Shooter", uri = "", webUrl = "")
                ),
                log = null,
                eventLog = null,
                complaint = null,
                supportedPlatforms = null,
                itunesId = null,
                recText = "Exciting action game!",
                videoResource = null,
                icon = null,
                releasedTime = null
            ), sort = 1, description = "Join epic battles and become a hero!", cover = Cover(
                url = "https://img.tapimg.net/market/images/11f0df847f02f476da2b13fb484fa806.png?imageView2/0/w/1080/h/608/format/jpg/interlace/1/ignore-error/1&t=1",
                mediumUrl = "https://img.tapimg.net/market/images/11f0df847f02f476da2b13fb484fa806.png?imageView2/0/w/720/h/405/format/jpg/interlace/1/ignore-error/1&t=1",
                smallUrl = "",
                originalUrl = "",
                originalFormat = "png",
                width = 1920,
                height = 1080,
                color = "#222222"
            ), title = "Hero Game", eventEndTime = null, video = null, videoId = null
        )
    )
}