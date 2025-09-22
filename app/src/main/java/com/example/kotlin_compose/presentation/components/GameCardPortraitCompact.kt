package com.example.kotlin_compose.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.text.style.TextAlign
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
import com.example.kotlin_compose.data.network.models.RecReason
import com.example.kotlin_compose.domain.models.Games


@Composable
fun GameCardPortraitCompact(
    modifier: Modifier = Modifier,
    game: Games,
    onItemClick: (Games) -> Unit
) {
    Column(
        modifier = modifier.background(colorResource(R.color.intl_v2_black)),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(min = 150.dp)
                .clickable { onItemClick(game) },
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.outlinedCardColors(),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row {
                AsyncImage(
                    modifier = Modifier
                        .width(55.dp)
                        .height(55.dp)
                        .sizeIn(minHeight = 30.dp),
                    model = game.app?.icon?.smallUrl,
                    contentDescription = "Trending movie poster",
                    contentScale = ContentScale.Crop,
                    placeholder = remember { ColorPainter(Color.Gray) },
                    alignment = Alignment.Center,
                )
                Text(
                    modifier = Modifier.width(145.dp),
                    text = game.app?.title ?: stringResource(id = R.string.unknown_game),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start
                )
            }

        }
    }
}

@Preview
@Composable
fun GamePreview(
    @PreviewParameter(GamePreviewParameterProvider::class) game: Games
) {
    GameCardPortraitCompact(game = game, onItemClick = {})
}

class GamePreviewParameterProvider : PreviewParameterProvider<Games> {
    override val values: Sequence<Games>
        get() = sequenceOf(
            Games(
                app = App(
                    banner = Banner(
                        color = "color_value",
                        height = 100,
                        mediumUrl = "https://img.tapimg.net/market/images/FgNV-WKYmmNL_L5-XYSx1T4o32iG.png/appicon_m?t=1",
                        originalFormat = "original_format_value",
                        originalUrl = "original_url_value",
                        smallUrl = "https://img.tapimg.net/market/images/FgNV-WKYmmNL_L5-XYSx1T4o32iG.png/appicon_s?t=1",
                        url = "https://img.tapimg.net/market/images/FgNV-WKYmmNL_L5-XYSx1T4o32iG.png/appicon?t=1",
                        width = 100
                    ),
                    canView = true,
                    complaint = null,
                    eventLog = null,
                    icon = null,
                    id = 0,
                    identifier = "identifier_text",
                    log = null,
                    stat = null,
                    tags = null,
                    title = "Game Title",
                    titleLabels = listOf("label1", "label2"),
                    uri = "uri_text"
                ),
                identification = "some_id",
                recReason = RecReason(type = 1, id = "rec_id", text = "Recommended"),
                type = "some_type"
            )
        )
}
