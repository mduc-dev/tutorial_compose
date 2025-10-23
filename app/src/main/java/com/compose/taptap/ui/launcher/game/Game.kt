package com.compose.taptap.ui.launcher.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.compose.taptap.R
import com.compose.taptap.core.designsystem.util.LoadingResult
import com.compose.taptap.core.model.App
import com.compose.taptap.core.model.Category
import com.compose.taptap.core.model.ListGameItem
import com.compose.taptap.core.navigation.AppComposeNavigator
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.core.navigation.currentComposeNavigator
import com.compose.taptap.core.network.model.SearchResponse
import com.compose.taptap.ui.components.CardGame
import com.compose.taptap.ui.components.LoadingScreen
import com.compose.taptap.ui.launcher.search.SearchViewModel
import com.compose.taptap.ui.theme.BlackF16
import com.compose.taptap.ui.theme.IntlCcDivider
import com.compose.taptap.ui.theme.IntlCcGreenPrimary
import com.compose.taptap.ui.theme.IntlV2Grey40
import com.compose.taptap.ui.theme.IntlV2Grey60
import com.compose.taptap.ui.theme.IntlV2Grey80
import com.compose.taptap.ui.theme.IntlV2Grey90
import com.compose.taptap.ui.theme.PPNeu
import com.compose.taptap.ui.theme.V3CommonPrimaryRed
import com.compose.taptap.ui.utils.DisabledInteractionSource
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

val topTabs: List<String> = listOf("Discover", "Top charts", "Calendar", "Gamelist")

val subTabs: List<String> = listOf("For you", "Editors' choice", "Arcade", "Strategy", "Casual")

@Composable
fun Game(
    gameViewModel: GameViewModel = koinViewModel<GameViewModel>(),
    searchViewModel: SearchViewModel = koinViewModel<SearchViewModel>()
) {
    val placeholderState by searchViewModel.searchUiState.collectAsStateWithLifecycle()
    val gameList = gameViewModel.gameUiStateFlow.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(placeholderState)

        HorizontalDivider(
            color = IntlCcDivider, thickness = 1.dp
        )

        GameContent(gameList)
    }
}

@Composable
private fun TopBar(
    placeholderState: LoadingResult<SearchResponse>,
) {
    val composeNavigator = currentComposeNavigator
    val placeholderText = when (placeholderState) {
        is LoadingResult.Success -> placeholderState.value.firstTextOrDefault()
        is LoadingResult.Loading -> "Loading..."
        is LoadingResult.Failure -> "Discover Superb Games"
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 6.dp, end = 6.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .height(32.dp)
                .weight(1f)
                .background(
                    color = IntlV2Grey90, shape = RoundedCornerShape(18.dp)
                )
                .clickable { composeNavigator.navigate(TapTapScreen.Search) },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.cw_toolbar_search_ic),
                contentDescription = "Search",
                tint = IntlV2Grey80,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(24.dp)
            )
            Text(
                text = placeholderText,
                maxLines = 1,
                fontWeight = FontWeight.Medium,
                fontFamily = PPNeu,
                fontSize = 14.sp,
                color = IntlV2Grey60,
                overflow = TextOverflow.Ellipsis
            )
        }
        NotificationBell(
            unreadCount = 5, onClick = { composeNavigator.navigate(TapTapScreen.Notifications) })
    }
}

@Composable
fun NotificationBell(
    unreadCount: Int, onClick: () -> Unit
) {
    BadgedBox(
        badge = {
            Badge(
                modifier = Modifier
                    .offset(x = -(7).dp)
                    .height(12.dp)
                    .width(16.dp),
                containerColor = V3CommonPrimaryRed,
                contentColor = White,
            ) {

                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                        .size(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = unreadCount.coerceAtMost(99).toString(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = PPNeu,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier.offset(x = 0.dp, y = -(1.5).dp)
                    )
                }
            }
        }, modifier = Modifier
            .width(46.dp)
            .height(24.dp)
    ) {
        Icon(
            painterResource(R.drawable.home_notification_ic),
            contentDescription = "Notification",
            tint = White,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onClick)
        )
    }
}


@Composable
fun GameContent(
    gameList: LazyPagingItems<ListGameItem>
) {

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = { topTabs.size })

    var selectedSubTab by remember { mutableIntStateOf(0) }

    PrimaryScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    pagerState.currentPage, matchContentSize = true
                ),
                color = IntlCcGreenPrimary,
                height = 3.dp,
                width = Dp.Unspecified,
                shape = RoundedCornerShape(50)
            )
        },
        containerColor = Color.Transparent,
        divider = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
    ) {
        topTabs.forEachIndexed { tabIndex, title ->
            Tab(
                selected = tabIndex == pagerState.currentPage,
                onClick = { scope.launch { pagerState.animateScrollToPage(tabIndex) } },
                selectedContentColor = White,
                unselectedContentColor = IntlV2Grey60,
                text = {
                    Text(
                        text = title,
                        fontWeight = if (pagerState.currentPage == tabIndex) FontWeight.Bold else FontWeight.Medium,
                        color = if (pagerState.currentPage == tabIndex) White
                        else IntlV2Grey40,
                        fontFamily = PPNeu,
                        fontSize = 15.sp
                    )
                },
                modifier = Modifier.height(34.dp),
                interactionSource = DisabledInteractionSource()
            )
        }
    }
    PageContent(
        pagerState = pagerState,
        selectedSubTab = selectedSubTab,
        onSubTabSelected = { selectedSubTab = it },
        games = gameList,
    )
}

@Composable
fun PageContent(
    pagerState: PagerState,
    selectedSubTab: Int,
    onSubTabSelected: (Int) -> Unit,
    games: LazyPagingItems<ListGameItem>,
) {
    HorizontalPager(
        state = pagerState, modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> DiscoverPage(
                selectedSubTab = selectedSubTab, onSubTabSelected = onSubTabSelected, games = games
            )

            1 -> Text("Top charts", color = White, modifier = Modifier.fillMaxSize())
            2 -> Text("Calendar", color = White, modifier = Modifier.fillMaxSize())
            3 -> Text("Game list", color = White, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun DiscoverPage(
    selectedSubTab: Int, onSubTabSelected: (Int) -> Unit, games: LazyPagingItems<ListGameItem>
) {
    val composeNavigator = currentComposeNavigator

    val loadState = games.loadState
    val isInitialLoad = loadState.refresh is LoadState.Loading && games.itemCount == 0

    if (isInitialLoad) {
        LoadingScreen(modifier = Modifier.background(BlackF16))
        return
    }

    val refreshError = loadState.refresh as? LoadState.Error
    if (refreshError != null && games.itemCount == 0) {
        PagingErrorState(
            message = refreshError.error.localizedMessage,
            onRetry = games::retry
        )
        return
    }

    val appendError = loadState.append as? LoadState.Error
    val isAppending = loadState.append is LoadState.Loading

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackF16)
    ) {
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(subTabs, key = { i, _ -> i }) { i, title ->
                    val isSelected = i == selectedSubTab
                    Text(
                        text = title,
                        modifier = Modifier
                            .background(
                                color = if (isSelected) White
                                else IntlV2Grey90, shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onSubTabSelected(i) }
                            .padding(horizontal = 9.dp, vertical = 1.dp),
                        color = if (isSelected) BlackF16 else LightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PPNeu)
                }
            }
        }
        items(
            count = games.itemCount, key = { index ->
                val game = games[index]
                game?.identification ?: game?.category?.id ?: game?.app?.id ?: "game-$index"
            }) { index ->
            val game = games[index]
            if (game == null) {
                AppendLoadingIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            } else {
                if (game.dailies != null && (game.type?.isDailiesType() == true)) {
                    val firstDailyGame = game.dailies?.list?.firstOrNull()
                    if (firstDailyGame != null) {
                        CardGame(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            game = firstDailyGame,
                            onClick = {
                                composeNavigator.navigate(TapTapScreen.GameDetail)
                            })
                    }
                }

                if (game.app != null) {
                    CardGame(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        game = game,
                        onClick = {
                            composeNavigator.navigate(TapTapScreen.GameDetail)
                        })
                }

                if (game.category != null && (game.type?.isCategoryType() == true)) {
                    CategorySection(
                        category = game.category!!, composeNavigator = composeNavigator
                    )
                }
            }
        }
        if (appendError != null && games.itemCount > 0) {
            item {
                PagingAppendErrorFooter(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    onRetry = games::retry
                )
            }
        }
        if (isAppending && games.itemCount > 0) {
            item {
                AppendLoadingIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun PagingErrorState(
    message: String?,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackF16)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message ?: "Something went wrong.",
            color = White,
            fontFamily = PPNeu,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tap to retry",
            color = IntlCcGreenPrimary,
            fontFamily = PPNeu,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .clickable { onRetry() }
                .padding(horizontal = 24.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun PagingAppendErrorFooter(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
) {
    Column(
        modifier = modifier.wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Couldn't load more games.",
            color = White,
            fontFamily = PPNeu,
            fontSize = 14.sp
        )
        Text(
            text = "Retry",
            color = IntlCcGreenPrimary,
            fontFamily = PPNeu,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .clickable { onRetry() }
                .padding(horizontal = 20.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun AppendLoadingIndicator(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.wrapContentSize(Alignment.Center),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            color = IntlCcGreenPrimary,
            strokeWidth = 2.dp,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun CategorySection(
    category: Category,
    composeNavigator: AppComposeNavigator<TapTapScreen>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.title,
                    color = White,
                    fontFamily = PPNeu,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                category.user.let { user ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AsyncImage(
                            model = user.avatar,
                            contentDescription = user.name,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = user.name,
                            color = White,
                            fontFamily = PPNeu,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val categoryItems = category.list
        if (categoryItems.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(
                    categoryItems, key = { index, item ->
                        item.id.let { "cat-item-$it" }
                    }) { _, item ->
                    CategoryGameItem(item = item, composeNavigator = composeNavigator)
                }
            }
        }
    }
}

@Composable
private fun CategoryGameItem(
    item: App,
    composeNavigator: AppComposeNavigator<TapTapScreen>,
) {
    Column(
        modifier = Modifier
            .width(84.dp)
            .clickable { composeNavigator.navigate(TapTapScreen.GameDetail) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        AsyncImage(
            model = item.icon?.mediumUrl ?: item.icon?.smallUrl,
            contentDescription = item.title,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(16.dp)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
        Text(
            text = item.title,
            color = White,
            fontFamily = PPNeu,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

private fun String?.isCategoryType(): Boolean =
    this?.contains("category", ignoreCase = true) == true

private fun String?.isDailiesType(): Boolean = this?.contains("dailies", ignoreCase = true) == true
