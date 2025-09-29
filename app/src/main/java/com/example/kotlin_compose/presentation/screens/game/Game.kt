package com.example.kotlin_compose.presentation.screens.game

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kotlin_compose.R
import com.example.kotlin_compose.data.network.utils.ApiResult
import com.example.kotlin_compose.domain.models.Games
import com.example.kotlin_compose.presentation.components.CardGame
import com.example.kotlin_compose.presentation.components.NoExistData
import com.example.kotlin_compose.presentation.navigation.AppComposeNavigator
import com.example.kotlin_compose.presentation.navigation.TapTapScreens
import com.example.kotlin_compose.presentation.screens.search.SearchViewModel
import com.example.kotlin_compose.presentation.utils.DisabledInteractionSource
import com.example.kotlin_compose.ui.theme.BlackF16
import com.example.kotlin_compose.ui.theme.PPNeu
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

val topTabs: List<String> = listOf("Discover", "Top charts", "Calendar", "Gamelist")

val subTabs: List<String> = listOf("For you", "Editors' choice", "Arcade", "Strategy", "Casual")

@Composable
fun Game(
    composeNavigator: AppComposeNavigator,
    gameViewModel: GameViewModel = koinViewModel<GameViewModel>(),
    searchViewModel: SearchViewModel = koinViewModel<SearchViewModel>()
) {

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = { topTabs.size })

    var selectedSubTab by remember { mutableIntStateOf(0) }

    val trendingItems = gameViewModel.trendingGames.collectAsLazyPagingItems()

    val placeholderState by searchViewModel.searchUiState.collectAsState()

    val searchPlaceHolderText = remember(placeholderState) {
        when (placeholderState) {
            is ApiResult.Success -> (placeholderState as ApiResult.Success).data.firstTextOrDefault()
            is ApiResult.Error -> "Discover Superb Games"
            is ApiResult.Loading -> "Loading..."
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackF16)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(searchPlaceHolderText, composeNavigator)

        HorizontalDivider(
            color = colorResource(R.color.intl_cc_divider), thickness = 1.dp
        )

        PrimaryScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        pagerState.currentPage, matchContentSize = true
                    ),
                    color = colorResource(R.color.intl_cc_green_primary),
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
                    unselectedContentColor = colorResource(R.color.intl_v2_grey_60),
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (pagerState.currentPage == tabIndex) FontWeight.Bold else FontWeight.Medium,
                            color = if (pagerState.currentPage == tabIndex) White
                            else colorResource(R.color.intl_v2_grey_40),
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
            pagerState,
            composeNavigator,
            selectedSubTab,
            onSubTabSelected = { selectedSubTab = it },
            trendingItems,
            subTabs
        )
    }
}

@Composable
private fun TopBar(searchPlaceHolderText: String, composeNavigator: AppComposeNavigator) {
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
                    color = colorResource(R.color.intl_v2_grey_90),
                    shape = RoundedCornerShape(18.dp)
                )
                .clickable { composeNavigator.navigate(TapTapScreens.Search.route) },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.cw_toolbar_search_ic),
                contentDescription = "Search",
                tint = colorResource(R.color.intl_v2_grey_80),
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(24.dp)
            )
            Text(
                text = searchPlaceHolderText,
                maxLines = 1,
                fontWeight = FontWeight.Medium,
                fontFamily = PPNeu,
                fontSize = 14.sp,
                color = colorResource(R.color.intl_v2_grey_60),
                overflow = TextOverflow.Ellipsis
            )
        }
        NotificationBell(
            unreadCount = 5,
            onClick = { composeNavigator.navigate(TapTapScreens.Notifications.route) })
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
                containerColor = colorResource(R.color.v3_common_primary_red),
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
fun PageContent(
    pagerState: PagerState,
    composeNavigator: AppComposeNavigator,
    selectedSubTab: Int,
    onSubTabSelected: (Int) -> Unit,
    trendingItems: LazyPagingItems<Games>,
    subTabs: List<String>
) {
    HorizontalPager(
        state = pagerState, modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> DiscoverPage(
                subTabs = subTabs,
                selectedSubTab = selectedSubTab,
                onSubTabSelected = onSubTabSelected,
                trendingItems = trendingItems,
                composeNavigator = composeNavigator
            )

            1 -> Text("Top charts", color = White, modifier = Modifier.fillMaxSize())
            2 -> Text("Calendar", color = White, modifier = Modifier.fillMaxSize())
            3 -> Text("Game list", color = White, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun DiscoverPage(
    subTabs: List<String>,
    selectedSubTab: Int,
    onSubTabSelected: (Int) -> Unit,
    trendingItems: LazyPagingItems<Games>,
    composeNavigator: AppComposeNavigator
) {
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
                                else colorResource(R.color.intl_v2_grey_90),
                                shape = RoundedCornerShape(8.dp)
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

        when (trendingItems.loadState.refresh) {
            is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is LoadState.Error -> {
                item {
                    NoExistData(
                        modifier = Modifier.fillParentMaxSize(), subTextNull = "error"
                    )
                }
            }

            else -> {
                items(
                    count = trendingItems.itemCount,
                    key = { index -> trendingItems[index]?.identification ?: index } // ✅ stable key
                ) { index ->
                    trendingItems[index]?.let { game ->
                        CardGame(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            game = game,
                            onClick = {
                                composeNavigator.navigate(TapTapScreens.GameDetail.route)
                            })
                    }
                }

                when (trendingItems.loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentSize(Alignment.Center)
                            )
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            Text(
                                "Load more failed",
                                color = Color.Red,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}
