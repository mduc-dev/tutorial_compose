package com.example.kotlin_compose.presentation.screens.play

import InstantGameItem
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.kotlin_compose.R
import com.example.kotlin_compose.presentation.navigation.AppComposeNavigator
import com.example.kotlin_compose.presentation.navigation.TapTapScreens
import com.example.kotlin_compose.presentation.screens.login.customTabIndicatorOffset
import com.example.kotlin_compose.presentation.screens.welcome.nonScaledSp
import com.example.kotlin_compose.presentation.utils.DisabledInteractionSource
import com.example.kotlin_compose.ui.theme.BlackDisable
import com.example.kotlin_compose.ui.theme.BlackF16
import com.example.kotlin_compose.ui.theme.Green31
import com.example.kotlin_compose.ui.theme.PPNeu
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

val tabs = listOf("Games", "Recently")


@Composable
fun Play(
    composeNavigator: AppComposeNavigator,
    playViewModel: PlayViewModel = koinViewModel<PlayViewModel>()
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val density = LocalDensity.current


    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }

//    val playUiState = playViewModel.playUiState.collectAsStateWithLifecycle().value

    val instantGames = playViewModel.instantGames.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackF16)
            .statusBarsPadding()
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = BlackF16,
            divider = {},
            modifier = Modifier.padding(vertical = 16.dp),
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .customTabIndicatorOffset(
                            currentTabPosition = tabPositions[pagerState.currentPage],
                            tabWidth = tabWidths[pagerState.currentPage]
                        )
                        .clip(shape = MaterialTheme.shapes.large), color = Green31
                )
            },
        ) {
            tabs.forEachIndexed { tabIndex, item ->
                Tab(
                    selectedContentColor = Color.White,
                    unselectedContentColor = BlackDisable,
                    selected = tabIndex == pagerState.currentPage,
                    interactionSource = DisabledInteractionSource(),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(tabIndex)
                        }
                    }) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = PPNeu, fontWeight = FontWeight.Bold, fontSize = 17.sp
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 10.dp),
                        onTextLayout = { textLayoutResult ->
                            tabWidths[tabIndex] =
                                with(density) { textLayoutResult.size.width.toDp() }
                        })
                    HorizontalDivider(
                        thickness = 0.5.dp, color = colorResource(R.color.intl_v2_grey_20)
                    )
                }
            }
        }

        PageContent(
            pagerState,
            instantGames = instantGames,
            modifier = Modifier.weight(1f),
            composeNavigator
        )
    }

}

@Composable
fun PageContent(
    pagerState: PagerState,
    instantGames: LazyPagingItems<InstantGameItem>,
    modifier: Modifier = Modifier,
    composeNavigator: AppComposeNavigator
) {

    HorizontalPager(
        state = pagerState, beyondViewportPageCount = tabs.size, modifier = modifier
    ) { page ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            when (page) {
                0 -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(instantGames.itemCount) { index ->
                        instantGames[index]?.let { game ->
                            GameCard(game) {
                                composeNavigator.navigate(TapTapScreens.GameDetail.route)
                            }
                        }
                    }
                }

                1 -> {
                    Text("recently")
                }
            }
        }

    }
}


@Composable
fun GameCard(item: InstantGameItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = item.cover.mediumUrl.ifBlank { item.cover.url },
                contentDescription = item.title,
                modifier = Modifier.fillMaxSize(),
                placeholder = ColorPainter(Color.DarkGray),
            )

            val score = item.stats?.score.takeIf { it?.isNotBlank() == true }

            if (score != null) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clip(
                            RoundedCornerShape(
                                topStart = 8.dp, bottomEnd = 8.dp
                            )
                        )
                        .background(colorResource(id = R.color.greenPrimary))
                        .padding(horizontal = 3.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.thi_score_icon),
                        contentDescription = "rating_score",
                        modifier = Modifier.size(14.dp)
                    )

                    Text(
                        text = score,
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = PPNeu,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp.nonScaledSp
                    )
                }
            }
        }

        Text(
            text = item.title,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontFamily = PPNeu,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp.nonScaledSp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 6.dp)
        )

        Text(
            text = item.subtitle.ifBlank { "Unknown" },
            color = colorResource(R.color.intl_v2_grey_20),
            fontSize = 10.sp.nonScaledSp,
            fontFamily = PPNeu,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 12.sp
        )
    }
}

