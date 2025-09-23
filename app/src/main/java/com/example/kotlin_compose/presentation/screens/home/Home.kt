package com.example.kotlin_compose.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillParentMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cash.paging.compose.collectAsLazyPagingItems
import com.example.kotlin_compose.R
import com.example.kotlin_compose.presentation.components.NoExistData
import com.example.kotlin_compose.presentation.components.TrendingGameHeroCard
import com.example.kotlin_compose.presentation.navigation.AppComposeNavigator
import com.example.kotlin_compose.presentation.navigation.TapTapScreens
import com.example.kotlin_compose.ui.theme.BlackF16
import com.example.kotlin_compose.ui.theme.PPNeu
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Home(
    composeNavigator: AppComposeNavigator, viewModel: HomeViewModel = koinViewModel<HomeViewModel>()
) {
    val topTabs: List<String> = listOf("Discover", "Top charts", "Calendar", "Gamelist")
    val subTabs: List<String> = listOf("For you", "Editors' choice", "Arcade", "Strategy", "Casual")
    var selectedTopTab by remember { mutableIntStateOf(0) }
    var selectedSubTab by remember { mutableIntStateOf(0) }
    val homeUiState = viewModel.homeUiState.collectAsState().value
    val trendingItems = homeUiState.trendingGames?.collectAsLazyPagingItems()

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.intl_v2_black))
            .statusBarsPadding(),
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
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
                        .clickable(onClick = { composeNavigator.navigate(TapTapScreens.Search.route) }),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(R.drawable.cw_toolbar_search_ic),
                        contentDescription = "Search",
                        tint = colorResource(R.color.intl_v2_grey_80),
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .width(24.dp)
                            .height(24.dp)
                    )
                    Text(
                        "Discover Superb Games",
                        maxLines = 1,
                        fontStyle = FontStyle.Normal,
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
        item {
            HorizontalDivider(
                color = colorResource(R.color.intl_cc_divider), thickness = 1.dp
            )
        }
        item {
            TabRow(
                selectedTabIndex = selectedTopTab,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTopTab])
                            .height(3.dp),
                        color = colorResource(R.color.greenPrimary)
                    )
                },
                containerColor = Color.Transparent,
                divider = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
            ) {
                topTabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTopTab == index,
                        onClick = { selectedTopTab = index },
                        text = {
                            Text(
                                title,
                                fontWeight = if (selectedTopTab == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedTopTab == index) White else Gray,
                                fontStyle = FontStyle.Normal,
                                fontFamily = PPNeu,
                                fontSize = 15.sp
                            )
                        })
                }
            }
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(subTabs) { i, title ->
                    val isSelected = i == selectedSubTab
                    Text(
                        text = title,
                        modifier = Modifier
                            .background(
                                color = if (isSelected) White else colorResource(R.color.intl_v2_grey_90),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedSubTab = i }
                            .padding(horizontal = 9.dp, vertical = 3.dp),
                        color = if (isSelected) BlackF16 else LightGray,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PPNeu
                    )
                }
            }
        }
        when {
            homeUiState.isLoading -> {
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

            !homeUiState.error.isNullOrEmpty() -> {
                item {
                    NoExistData(
                        modifier = Modifier.fillParentMaxSize(),
                        subTextNull = homeUiState.error
                    )
                }
            }

            trendingItems != null -> {
                items(trendingItems.itemCount) { index ->
                    trendingItems[index]?.let { game ->
                        TrendingGameHeroCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            game = game,
                            onClick = {
                                composeNavigator.navigate("gameDetail/${game.identification}")
                            }
                        )
                    }
                }
            }
        }
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
                    .align(Alignment.TopEnd)
                    .offset(x = (-10).dp)
                    .height(12.dp),
                containerColor = colorResource(R.color.v3_common_primary_red),
                contentColor = White,
            ) {

                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                        .height(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = unreadCount.coerceAtMost(99).toString(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
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

