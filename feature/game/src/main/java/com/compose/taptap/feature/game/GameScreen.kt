package com.compose.taptap.feature.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.compose.taptap.core.designsystem.component.AppendLoadingIndicator
import com.compose.taptap.core.designsystem.component.CardGame
import com.compose.taptap.core.designsystem.component.GamePortraitItem
import com.compose.taptap.core.designsystem.component.LoadingScreen
import com.compose.taptap.core.designsystem.component.PagingAppendErrorFooter
import com.compose.taptap.core.designsystem.component.PagingErrorState
import com.compose.taptap.core.designsystem.component.SectionHeader
import com.compose.taptap.core.designsystem.component.TapTapChipGroup
import com.compose.taptap.core.designsystem.component.TapTapNotificationBell
import com.compose.taptap.core.designsystem.component.TapTapSearchBar
import com.compose.taptap.core.designsystem.component.TapTapTabRow
import com.compose.taptap.core.designsystem.theme.BlackF16
import com.compose.taptap.core.designsystem.theme.IntlCcDivider
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.DisableParentPagerSwipeConnection
import com.compose.taptap.core.designsystem.util.LoadingResult
import com.compose.taptap.core.model.Category
import com.compose.taptap.core.model.ListGameItem
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.core.navigation.currentComposeNavigator
import com.compose.taptap.feature.search.SearchViewModel
import kotlinx.coroutines.flow.flowOf
import org.koin.compose.viewmodel.koinViewModel

val topTabs: List<String> = listOf("Discover", "Top charts", "Calendar", "Gamelist")

val subTabs: List<String> = listOf("For you", "Editors' choice", "Arcade", "Strategy", "Casual")

@Composable
fun GameRoute(
    gameViewModel: GameViewModel = koinViewModel<GameViewModel>(),
) {
    val composeNavigator = currentComposeNavigator
    val searchViewModel = koinViewModel<SearchViewModel>()
    val placeholderState by searchViewModel.searchUiState.collectAsStateWithLifecycle()

    val gameList = gameViewModel.gameUiStateFlow.collectAsLazyPagingItems()

    var selectedTopTab by remember { mutableIntStateOf(0) }
    var selectedSubTab by remember { mutableIntStateOf(0) }

    val placeholder = when (val state = placeholderState) {
        is LoadingResult.Success -> state.value.firstTextOrDefault()
        is LoadingResult.Loading -> "Loading..."
        is LoadingResult.Failure -> "Discover Superb Games"
    }

    val uiState = remember(placeholder, selectedTopTab, selectedSubTab) {
        GameUiState(
            searchPlaceholder = placeholder,
            unreadNotifications = 5,
            selectedTopTab = selectedTopTab,
            selectedSubTab = selectedSubTab,
            isLoading = false
        )
    }

    GameScreen(
        uiState = uiState, gameList = gameList, onEvent = { event ->
            when (event) {
                GameUiEvent.OnSearchClick -> composeNavigator.navigate(TapTapScreen.Search)
                GameUiEvent.OnNotificationClick -> composeNavigator.navigate(TapTapScreen.Notifications)
                is GameUiEvent.OnTopTabClick -> selectedTopTab = event.index
                is GameUiEvent.OnSubTabClick -> selectedSubTab = event.index
                is GameUiEvent.OnGameClick -> composeNavigator.navigate(TapTapScreen.GameDetail)
                is GameUiEvent.OnCategoryClick -> composeNavigator.navigate(TapTapScreen.GameDetail)
                GameUiEvent.OnRetry -> gameList.retry()
            }
        })
}

@Composable
fun GameScreen(
    uiState: GameUiState,
    gameList: LazyPagingItems<ListGameItem>,
    onEvent: (GameUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(
            placeholderText = uiState.searchPlaceholder,
            unreadCount = uiState.unreadNotifications,
            onSearchClick = { onEvent(GameUiEvent.OnSearchClick) },
            onNotificationClick = { onEvent(GameUiEvent.OnNotificationClick) })

        HorizontalDivider(
            color = IntlCcDivider, thickness = 1.dp
        )

        GameContent(
            gameList = gameList,
            selectedTopTab = uiState.selectedTopTab,
            selectedSubTab = uiState.selectedSubTab,
            onTopTabClick = { index -> onEvent(GameUiEvent.OnTopTabClick(index)) },
            onSubTabClick = { index -> onEvent(GameUiEvent.OnSubTabClick(index)) },
            onGameClick = { gameId -> onEvent(GameUiEvent.OnGameClick(gameId)) },
            onCategoryClick = { categoryId -> onEvent(GameUiEvent.OnCategoryClick(categoryId)) },
            onRetry = { onEvent(GameUiEvent.OnRetry) })
    }
}

@Composable
private fun TopBar(
    placeholderText: String,
    unreadCount: Int,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 6.dp, end = 6.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TapTapSearchBar(
            placeholderText = placeholderText,
            onSearchClick = onSearchClick,
            modifier = Modifier.weight(1f)
        )

        TapTapNotificationBell(
            unreadCount = unreadCount, onClick = onNotificationClick
        )
    }
}




@Composable
fun GameContent(
    gameList: LazyPagingItems<ListGameItem>,
    selectedTopTab: Int,
    selectedSubTab: Int,
    onTopTabClick: (Int) -> Unit,
    onSubTabClick: (Int) -> Unit,
    onGameClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { topTabs.size })

    Column(modifier = modifier.fillMaxSize()) {
        // Top tabs
        // Top tabs
        TapTapTabRow(
            tabs = topTabs,
            pagerState = pagerState,
            onTabClick = onTopTabClick
        )

        // Content pager
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> DiscoverPageContent(
                    selectedSubTab = selectedSubTab,
                    onSubTabSelected = onSubTabClick,
                    games = gameList,
                    onGameClick = onGameClick,
                    onCategoryClick = onCategoryClick,
                    onRetry = onRetry
                )

                else -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Page ${page + 1}", color = White)
                }
            }
        }
    }
}

@Composable
fun DiscoverPageContent(
    selectedSubTab: Int,
    onSubTabSelected: (Int) -> Unit,
    games: LazyPagingItems<ListGameItem>,
    onGameClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val loadState = games.loadState
    val isInitialLoad = loadState.refresh is LoadState.Loading && games.itemCount == 0

    if (isInitialLoad) {
        LoadingScreen(modifier = Modifier.background(BlackF16))
        return
    }

    val refreshError = loadState.refresh as? LoadState.Error
    if (refreshError != null && games.itemCount == 0) {
        PagingErrorState(
            message = refreshError.error.localizedMessage, onRetry = onRetry
        )
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BlackF16),
    ) {
        // Sub-tabs as first item - scrolls with content
        item {
            TapTapChipGroup(
                items = subTabs,
                selectedIndex = selectedSubTab,
                onItemClick = onSubTabSelected
            )
        }

        items(games.itemCount) { index ->
            games[index]?.let { item ->
                when {
                    item.type.isCategoryType() -> {
                        item.category?.let { category ->
                            CategorySection(
                                category = category,
                                onGameClick = onGameClick,
                                onCategoryClick = onCategoryClick
                            )
                        }
                    }

                    else -> {
                        item.app?.let { app ->
                            CardGame(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                game = item, onClick = { onGameClick(app.id.toString()) })
                        }
                    }
                }
            }
        }

        val appendState = loadState.append
        when (appendState) {
            is LoadState.Loading -> {
                item {
                    AppendLoadingIndicator()
                }
            }

            is LoadState.Error -> {
                item {
                    PagingAppendErrorFooter(onRetry = onRetry)
                }
            }

            else -> {}
        }
    }
}



@Composable
private fun CategorySection(
    category: Category,
    onGameClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        SectionHeader(
            title = category.title,
            onMoreClick = { onCategoryClick(category.id.toString()) }
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(DisableParentPagerSwipeConnection),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(category.list.size) { index ->
                GamePortraitItem(
                    item = category.list[index],
                    onGameClick = onGameClick
                )
            }
        }
    }
}



private fun String?.isCategoryType(): Boolean =
    this?.contains("category", ignoreCase = true) == true

private fun String?.isDailiesType(): Boolean = this?.contains("dailies", ignoreCase = true) == true




@Preview(showBackground = true, backgroundColor = 0xFF161616)
@Composable
private fun GameScreenPreview() {
    val games = flowOf(PagingData.from(emptyList<ListGameItem>())).collectAsLazyPagingItems()
    TapTapTheme {
        GameScreen(
            uiState = GameUiState(
                searchPlaceholder = "Discover Superb Games",
                unreadNotifications = 5,
                selectedTopTab = 0,
                selectedSubTab = 0,
                isLoading = false
            ), gameList = games, onEvent = {} // No-op for preview
        )
    }
}

