package com.example.kotlin_compose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
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
import com.example.kotlin_compose.data.network.models.Rating
import com.example.kotlin_compose.data.network.models.Stat
import com.example.kotlin_compose.data.network.models.Tag
import com.example.kotlin_compose.domain.models.Games
import com.example.kotlin_compose.presentation.components.ButtonSize.MD
import com.example.kotlin_compose.presentation.components.Variant.SOLID
import com.example.kotlin_compose.ui.theme.PPNeu
import java.text.NumberFormat

@Composable
fun TrendingGameHeroCard(
    game: Games,
    modifier: Modifier = Modifier,
    onClick: (Games) -> Unit,
) {
    val app = game.app
    val bannerUrl = app?.banner?.mediumUrl
        ?: app?.banner?.url
        ?: app?.icon?.mediumUrl
        ?: app?.icon?.url
    val rating = app?.stat?.rating?.score?.takeIf { it.isNotBlank() }
    val reviewCount = app?.stat?.reviewCount?.takeIf { it > 0 }
    val formattedReviewCount = remember(reviewCount) {
        reviewCount?.let { NumberFormat.getInstance().format(it) }
    }
    val tagLine = app?.tags?.map { it.value }?.filter { it.isNotBlank() }?.take(3)?.joinToString(" • ")
    val recommendation = game.recReason?.text?.takeIf { it.isNotBlank() }

    Card(
        modifier = modifier.clickable { onClick(game) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.intl_v2_grey_90)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            AsyncImage(
                model = bannerUrl,
                contentDescription = game.app?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                placeholder = remember { ColorPainter(Color.DarkGray) }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.intl_v2_grey_90))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (recommendation != null) {
                    Text(
                        text = recommendation,
                        color = colorResource(R.color.intl_v2_grey_60),
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = PPNeu,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = app?.title ?: stringResource(id = R.string.unknown_game),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = PPNeu,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp
                )
                if (!tagLine.isNullOrEmpty()) {
                    Text(
                        text = tagLine,
                        color = colorResource(R.color.intl_v2_grey_60),
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = PPNeu,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        if (rating != null) {
                            Text(
                                text = "Rating $rating",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = PPNeu,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Normal
                            )
                        }
                        if (formattedReviewCount != null) {
                            Text(
                                text = "$formattedReviewCount reviews",
                                color = colorResource(R.color.intl_v2_grey_60),
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = PPNeu,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            )
                        }
                    }
                    DDButton(
                        label = "Get",
                        variant = SOLID,
                        size = MD,
                        onPress = { onClick(game) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TrendingGameHeroCardPreview(
    @PreviewParameter(TrendingGameHeroCardPreviewProvider::class) game: Games
) {
    TrendingGameHeroCard(game = game, onClick = {})
}

class TrendingGameHeroCardPreviewProvider : PreviewParameterProvider<Games> {
    override val values: Sequence<Games> = sequenceOf(
        Games(
            type = "",
            identification = "sample",
            app = App(
                id = 0,
                identifier = "identifier",
                title = "Hero Game",
                titleLabels = emptyList(),
                icon = null,
                uri = null,
                canView = true,
                stat = Stat(
                    rating = Rating(score = "4.8", max = 5, latestScore = "4.9", latestVersionScore = "4.7"),
                    voteInfo = null,
                    hitsTotal = 0,
                    reviewCount = 1234,
                    fansCount = 0,
                    userWantCount = 0,
                    userPlayedCount = 0,
                    userPlayingCount = 0,
                    reserveCount = 0
                ),
                banner = Banner(
                    url = "https://img.tapimg.net/market/images/FgNV-WKYmmNL_L5-XYSx1T4o32iG.png/appicon?t=1",
                    mediumUrl = "https://img.tapimg.net/market/images/FgNV-WKYmmNL_L5-XYSx1T4o32iG.png/appicon_m?t=1",
                    smallUrl = null,
                    originalUrl = null,
                    originalFormat = null,
                    width = 0,
                    height = 0,
                    color = null
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
                recText = null,
                videoResource = null
            ),
            recReason = null
        )
    )
}
