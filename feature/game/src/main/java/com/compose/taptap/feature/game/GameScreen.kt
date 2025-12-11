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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.component.molecules.chips.TapTapChipGroup
import com.compose.taptap.core.designsystem.component.molecules.notifications.TapTapNotificationBell
import com.compose.taptap.core.designsystem.component.molecules.search.TapTapSearchBar
import com.compose.taptap.core.designsystem.component.molecules.state.LoadingView
import com.compose.taptap.core.designsystem.component.molecules.tabs.TapTapTabRow
import com.compose.taptap.core.designsystem.component.organisms.game.CardGame
import com.compose.taptap.core.designsystem.component.organisms.game.FeaturedGamesPager
import com.compose.taptap.core.designsystem.component.organisms.game.GameCardUiState
import com.compose.taptap.core.designsystem.component.organisms.game.toCardUiState
import com.compose.taptap.core.designsystem.component.organisms.paging.AppendLoadingIndicator
import com.compose.taptap.core.designsystem.component.organisms.paging.PagingAppendErrorFooter
import com.compose.taptap.core.designsystem.component.organisms.paging.PagingErrorState
import com.compose.taptap.core.designsystem.theme.IntlCcDivider
import com.compose.taptap.core.designsystem.theme.TapTapDimens.ListBottomPadding
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.LoadingResult
import com.compose.taptap.core.model.ListGameItem
import com.compose.taptap.core.model.firstTextOrDefault
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.core.navigation.currentComposeNavigator
import com.compose.taptap.core.preview.PreviewUtils
import com.compose.taptap.core.preview.TapTapPreviewTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.flowOf
import org.koin.compose.viewmodel.koinViewModel


val topTabs: List<String> = listOf("Discover", "Top charts", "Calendar", "Gamelist")

val subTabs: List<String> = listOf("For you", "Editors' choice", "Arcade", "Strategy", "Casual")

@Composable
fun GameRoute(
    gameViewModel: GameViewModel = koinViewModel<GameViewModel>(),
) {
    val composeNavigator = currentComposeNavigator
    val placeholderState by gameViewModel.searchPlaceholderState.collectAsStateWithLifecycle(
        initialValue = LoadingResult.Loading
    )

    val gameList = gameViewModel.gameUiStateFlow.collectAsLazyPagingItems()
    val featuredGames by gameViewModel.featuredGames.collectAsStateWithLifecycle()

    var selectedTopTab by remember { mutableIntStateOf(0) }
    var selectedSubTab by remember { mutableIntStateOf(0) }

    val placeholder = when (val state = placeholderState) {
        is LoadingResult.Success -> state.value.firstTextOrDefault()
        else -> "Loading..."
    }

    val uiState = remember(placeholder, selectedTopTab, selectedSubTab) {
        GameUiState(
            searchPlaceholder = placeholder,
            unreadNotifications = 5,
            selectedTopTab = selectedTopTab,
            selectedSubTab = selectedSubTab,
        )
    }

    CompositionLocalProvider(LocalGameList provides gameList) {
        GameScreen(
            uiState = uiState,
            featuredGames = featuredGames,
            onEvent = { event ->
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
}

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    uiState: GameUiState,
    featuredGames: ImmutableList<GameCardUiState.Featured> = persistentListOf(),
    onEvent: (GameUiEvent) -> Unit,
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
            selectedSubTab = uiState.selectedSubTab,
            featuredGames = featuredGames,
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
    selectedSubTab: Int,
    featuredGames: ImmutableList<GameCardUiState.Featured>,
    onTopTabClick: (Int) -> Unit,
    onSubTabClick: (Int) -> Unit,
    onGameClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { topTabs.size })

    Column(modifier = modifier.fillMaxSize()) {
        // Top tabs
        TapTapTabRow(
            tabs = topTabs, pagerState = pagerState, onTabClick = onTopTabClick
        )

        // Content pager
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> DiscoverPageContent(
                    selectedSubTab = selectedSubTab,
                    featuredGames = featuredGames,
                    onSubTabSelected = onSubTabClick,
                    onGameClick = onGameClick,
                    onCategoryClick = onCategoryClick,
                    onRetry = onRetry
                )

                else -> Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    TapTapText("Page ${page + 1}", color = TapTapTheme.colors.onBackground)
                }
            }
        }
    }
}

@Composable
fun DiscoverPageContent(
    selectedSubTab: Int,
    featuredGames: ImmutableList<GameCardUiState.Featured>,
    onSubTabSelected: (Int) -> Unit,
    onGameClick: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val games = LocalGameList.current
    val loadState = games.loadState
    val isInitialLoad = loadState.refresh is LoadState.Loading && games.itemCount == 0

    if (isInitialLoad) {
        LoadingView(modifier = Modifier.background(TapTapTheme.colors.background))
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
            .background(TapTapTheme.colors.background)
            .testTag("game_list"),
        contentPadding = PaddingValues(bottom = ListBottomPadding)
    ) {
        // Sub-tabs as first item - scrolls with content
        item {
            TapTapChipGroup(
                items = subTabs, selectedIndex = selectedSubTab, onItemClick = onSubTabSelected
            )
        }

        // Display featured games pager if any exist
        if (featuredGames.isNotEmpty()) {
            item {
                FeaturedGamesPager(
                    featuredGames = featuredGames,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = TapTapTheme.spacing.medium),
                    onClick = onGameClick
                )
            }
        }

        // Display other items (categories and regular games)
        items(games.itemCount) { index ->
            games[index]?.let { item ->
                when {
                    item.category != null -> {
                        CardGame(
                            uiState = item.toCardUiState(),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = onGameClick,
                            onCategoryClick = onCategoryClick
                        )
                    }

                    item.app != null -> {
                        CardGame(
                            uiState = item.toCardUiState(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            onClick = onGameClick
                        )
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


@Preview()
@Composable
private fun GameScreenPreview() {
    val games = flowOf(PagingData.from(PreviewUtils.mockGames.map {
        ListGameItem(app = it)
    })).collectAsLazyPagingItems()

    TapTapPreviewTheme {
        CompositionLocalProvider(LocalGameList provides games) {
            GameScreen(
                uiState = GameUiState(
                    searchPlaceholder = "Discover Superb Games",
                    unreadNotifications = 5,
                    selectedTopTab = 0,
                    selectedSubTab = 0,
                ),
                featuredGames = persistentListOf(),
                onEvent = {} // No-op for preview
            )
        }
    }
}
