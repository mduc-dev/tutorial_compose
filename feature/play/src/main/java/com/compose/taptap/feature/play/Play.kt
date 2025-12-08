package com.compose.taptap.feature.play

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.divider.TapTapDivider
import com.compose.taptap.core.designsystem.theme.BlackF16
import com.compose.taptap.core.designsystem.theme.GreenPrimary
import com.compose.taptap.core.designsystem.theme.IntlCcGreenPrimary
import com.compose.taptap.core.designsystem.theme.IntlV2Grey20
import com.compose.taptap.core.designsystem.theme.IntlV2Grey60
import com.compose.taptap.core.designsystem.theme.TapTapDimens.GridSpacing
import com.compose.taptap.core.designsystem.theme.TapTapDimens.ScoreIconSize
import com.compose.taptap.core.designsystem.theme.TapTapDimens.TitleTopPadding
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.theme.WhitePrimary
import com.compose.taptap.core.model.InstantGameItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

val tabPlays = persistentListOf("Games", "Recently")

@Composable
fun Play(
    playViewModel: PlayViewModel = koinViewModel<PlayViewModel>(),
    onToggleFlip: (Boolean, String?) -> Unit = { _, _ -> }
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabPlays.size })
    val spacing = TapTapTheme.spacing

    val instantGames = playViewModel.instantGames.collectAsLazyPagingItems()

    val recentlyGames by playViewModel.recentlyGames.collectAsStateWithLifecycle()
    val stableRecentlyGames = remember(recentlyGames) { recentlyGames.toImmutableList() }

    val randomInstantGame by playViewModel.randomInstantGame.collectAsStateWithLifecycle()

    // Hoist the grid state to observe scroll for flip logic
    val gamesGridState = rememberLazyGridState()

    // Derived state for scroll threshold (10th row -> index 18+)
    val isBeyondThreshold by remember {
        derivedStateOf {
            gamesGridState.firstVisibleItemIndex >= 18
        }
    }

    // Effect to handle flip logic based on scroll AND data availability
    LaunchedEffect(isBeyondThreshold, randomInstantGame) {
        if (isBeyondThreshold) {
            if (randomInstantGame != null) {
                onToggleFlip(true, randomInstantGame)
            } else {
                playViewModel.fetchRandomInstantGame()
            }
        } else {
            onToggleFlip(false, null)
        }
    }

    CompositionLocalProvider(LocalInstantGameList provides instantGames) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(TapTapTheme.colors.background)
        ) {
            PlayTabRow(
                tabs = tabPlays,
                pagerState = pagerState,
                onTabClick = { index ->
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                modifier = Modifier.padding(vertical = spacing.mediumLarge)
            )
            PageContent(
                pagerState,
                gamesGridState = gamesGridState, // Pass hoisted state
                recentlyGames = stableRecentlyGames,
                onPlayGame = { game ->
                    playViewModel.onPLayGame(game)
                },
                onRandomPlay = {
                    playViewModel.fetchRandomInstantGame()
                    scope.launch {
                        pagerState.animateScrollToPage(0) // Switch to Games tab
                    }
                }
            )
        }
    }
}


@Composable
fun PageContent(
    pagerState: PagerState,
    gamesGridState: LazyGridState,
    recentlyGames: ImmutableList<InstantGameItem>,
    modifier: Modifier = Modifier,
    onPlayGame: (InstantGameItem) -> Unit,
    onRandomPlay: () -> Unit
) {
    val instantGames = LocalInstantGameList.current

    HorizontalPager(
        state = pagerState, beyondViewportPageCount = tabPlays.size, modifier = modifier
    ) { page ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(horizontal = TapTapTheme.spacing.mediumLarge),
            contentAlignment = Alignment.TopCenter
        ) {
            when (page) {
                0 -> LazyVerticalGrid(
                    state = gamesGridState,
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(GridSpacing),
                    verticalArrangement = Arrangement.spacedBy(GridSpacing),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(
                        count = instantGames.itemCount,
                        key = { index -> instantGames[index]?.identification ?: index }) { index ->
                        instantGames[index]?.let { game ->
                            CardGame(game, onClick = { onPlayGame(game) })
                        }
                    }
                }

                1 -> {
                    if (recentlyGames.isEmpty()) {
                        RecentlyEmptyState(
                            onRandomPlay = onRandomPlay
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(GridSpacing),
                            verticalArrangement = Arrangement.spacedBy(GridSpacing),
                            contentPadding = PaddingValues(bottom = 100.dp)
                        ) {
                            items(
                                count = recentlyGames.size,
                                key = { index -> recentlyGames[index].identification }) { index ->
                                val game = recentlyGames[index]
                                CardGame(game, onClick = { onPlayGame(game) })
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun CardGame(item: InstantGameItem, onClick: () -> Unit) {
    val spacing = TapTapTheme.spacing
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(TapTapShape.corners.large),
            contentAlignment = Alignment.Center
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
                                topStart = spacing.small, bottomEnd = spacing.small
                            )
                        )
                        .background(GreenPrimary)
                        .padding(horizontal = spacing.tiny, vertical = spacing.xSmall),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.xSmall)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.thi_score_icon),
                        contentDescription = "rating_score",
                        modifier = Modifier.size(ScoreIconSize),
                        tint = WhitePrimary
                    )

                    Text(
                        text = score,
                        color = WhitePrimary,
                        style = TapTapTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        Text(
            text = item.title,
            color = TapTapTheme.colors.onBackground,
            style = TapTapTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = TitleTopPadding)
        )

        Text(
            text = item.subtitle.ifBlank { "Unknown" },
            color = IntlV2Grey20,
            style = TapTapTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun RecentlyEmptyState(
    modifier: Modifier = Modifier,
    onRandomPlay: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(TapTapTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.thi_taptap_logo),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(TapTapTheme.spacing.mediumLarge))
        Text(
            text = "Your Game Profile",
            style = TapTapTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.W500,
                color = TapTapTheme.colors.onBackground
            )
        )
        Spacer(modifier = Modifier.height(TapTapTheme.spacing.medium))
        Text(
            text = "Start your first game adventure!",
            style = TapTapTheme.typography.bodyLarge.copy(
                color = TapTapTheme.colors.surfaceContainerHighest,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(horizontal = TapTapTheme.spacing.large)
        )
        Spacer(modifier = Modifier.height(TapTapTheme.spacing.large))
        Button(
            onClick = onRandomPlay,
            shape = TapTapShape.corners.circle,
            contentPadding = PaddingValues(
                horizontal = TapTapTheme.spacing.large,
                vertical = TapTapTheme.spacing.medium
            )
        ) {
            Text(
                text = "Random Play",
                style = TapTapTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = BlackF16
                )
            )
        }
    }
}

@Composable
private fun PlayTabRow(
    tabs: ImmutableList<String>,
    pagerState: PagerState,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val tabWidths = remember { mutableStateListOf<Dp>() }
    val tabOffsets = remember { mutableStateListOf<Dp>() }

    if (tabWidths.size != tabs.size) {
        tabWidths.clear()
        tabOffsets.clear()
        tabs.forEach { _ ->
            tabWidths.add(0.dp)
            tabOffsets.add(0.dp)
        }
    }

    val currentTabIndex = pagerState.currentPage
    val indicatorWidth by animateDpAsState(
        targetValue = if (tabWidths.isNotEmpty() && currentTabIndex in tabWidths.indices) {
            tabWidths[currentTabIndex]
        } else 0.dp, label = "indicatorWidth"
    )
    val indicatorOffset by animateDpAsState(
        targetValue = if (tabOffsets.isNotEmpty() && currentTabIndex in tabOffsets.indices) {
            tabOffsets[currentTabIndex]
        } else 0.dp, label = "indicatorOffset"
    )

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = pagerState.currentPage == index
                    val color = if (isSelected) TapTapTheme.colors.onBackground else IntlV2Grey60
                    val style = TapTapTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                    )

                    Column(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onTabClick(index) }
                            .padding(horizontal = TapTapTheme.spacing.medium)
                            .onGloballyPositioned { coordinates ->
                                val width = with(density) { coordinates.size.width.toDp() }
                                val offset = with(density) { coordinates.positionInParent().x.toDp() }
                                
                                if (index < tabWidths.size) {
                                    if (tabWidths[index] != width) tabWidths[index] = width
                                    if (tabOffsets[index] != offset) tabOffsets[index] = offset
                                }
                            }
                    ) {
                        Text(
                            text = title,
                            style = style,
                            color = color,
                            modifier = Modifier.padding(bottom = TapTapTheme.spacing.mediumSmall)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = indicatorOffset)
                    .width(indicatorWidth)
                    .height(3.dp)
                    .background(IntlCcGreenPrimary, TapTapShape.corners.pill)
            )
        }
        
        TapTapDivider(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

