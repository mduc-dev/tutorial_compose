package com.example.kotlin_compose.presentation.screens.account

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin_compose.R
import com.example.kotlin_compose.presentation.components.NoExistData
import com.example.kotlin_compose.presentation.utils.isEmpty
import com.example.kotlin_compose.ui.theme.Black1A
import com.example.kotlin_compose.ui.theme.BlackDisable
import com.example.kotlin_compose.ui.theme.BlackF3
import com.example.kotlin_compose.ui.theme.Green1a
import com.example.kotlin_compose.ui.theme.Green31
import com.example.kotlin_compose.ui.theme.PPNeu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

val tabs = listOf("Posts", "Saved", "Drafts")
val enumValuesChip = listOf("All", "Gamelists", "Articles", "Videos")

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Account() {
    val listState = rememberLazyListState()
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

    Scaffold(containerColor = Black1A) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
        ) {
            item {
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage, edgePadding = 20.dp,
                    containerColor = Color.Transparent,
                    divider = {},
                    modifier = Modifier.padding(top = 16.dp,bottom = 8.dp),
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
                        Tab(selectedContentColor = Color.White,
                            unselectedContentColor = BlackDisable,
                            selected = tabIndex == pagerState.currentPage,
                            interactionSource = DisabledInteractionSource(),
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(tabIndex)
                                }
                            }) {
                            Text(text = item,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontFamily = PPNeu,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                ),
                                modifier = Modifier
                                    .align(CenterHorizontally)
                                    .padding(horizontal = 32.dp, vertical = 8.dp),
                                onTextLayout = { textLayoutResult ->
                                    tabWidths[tabIndex] =
                                        with(density) { textLayoutResult.size.width.toDp() }
                                })
                        }
                    }
                }
                PageContent(pagerState)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageContent(pagerState: PagerState) {
    val styleTextBtn: TextStyle = MaterialTheme.typography.titleMedium.copy(
        fontFamily = PPNeu, fontWeight = FontWeight.Bold
    )
    var selectedFilter by remember { mutableStateOf(enumValuesChip.first()) }

    HorizontalPager(
        state = pagerState, beyondBoundsPageCount = 10
    ) { page ->
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                enumValuesChip.forEach {
                    FilterChip(
                        selected = it == selectedFilter,
                        onClick = {
                            selectedFilter = it
                        },
                        label = { Text(text = it, style = styleTextBtn) },
                        interactionSource = DisabledInteractionSource(),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Green1a,
                            labelColor = BlackDisable,
                            selectedLabelColor = Color.White,
                            containerColor = Black1A,

                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = it == selectedFilter,
                            borderColor = BlackF3,
                            selectedBorderColor = Color.Transparent,
                            selectedBorderWidth = 0.dp,

                        ),
                        shape = MaterialTheme.shapes.small
                    )

                }

            }
            if (isEmpty("")) {
                NoExistData(
                    subTextNull = "Write a post to start your profile’s never-ending journey.",
                    painterResourceName = R.drawable.sad_icon_top,
                    stringResourceName = R.string.sad_icon_top
                )
            } else {
                Text(text = "content list here", color = Color.White)
            }
        }
    }
}

fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition, tabWidth: Dp
): Modifier = composed(inspectorInfo = debugInspectorInfo {
    name = "customTabIndicatorOffset"
    value = currentTabPosition
}) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
        label = ""
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
        label = ""
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}


class DisabledInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}

@Preview(apiLevel = 33, showBackground = true, backgroundColor = 0xFF1A1A1A, showSystemUi = true)
@Composable
fun AccountPreview() {
    Account()
}