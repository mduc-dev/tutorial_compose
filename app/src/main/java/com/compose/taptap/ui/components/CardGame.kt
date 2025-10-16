package com.compose.taptap.ui.components

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.compose.taptap.R
import com.compose.taptap.data.model.DailiesItem
import com.compose.taptap.data.model.ListItem
import com.compose.taptap.ui.launcher.welcome.nonScaledSp
import com.compose.taptap.ui.theme.BlackF16
import com.compose.taptap.ui.theme.GreenPrimary
import com.compose.taptap.ui.theme.IntlV2Grey20
import com.compose.taptap.ui.theme.IntlV2Grey40
import com.compose.taptap.ui.theme.PPNeu
import com.compose.taptap.ui.theme.WhitePrimary


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
    game: ListItem,
    modifier: Modifier = Modifier,
    onClick: (ListItem) -> Unit,
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
            modifier = Modifier.padding(vertical = 10.dp), thickness = 0.2.dp, color = IntlV2Grey20
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
        colors = CardDefaults.cardColors(
            containerColor = BlackF16,
            contentColor = WhitePrimary
        ),
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

