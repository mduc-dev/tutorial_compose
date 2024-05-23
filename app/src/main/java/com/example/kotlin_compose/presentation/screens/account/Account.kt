package com.example.kotlin_compose.presentation.screens.account

import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.example.kotlin_compose.presentation.components.NoExistData
import com.example.kotlin_compose.ui.theme.PPNeu
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.composed
import com.example.kotlin_compose.presentation.components.CustomButton
import com.example.kotlin_compose.ui.theme.BlackDisable
import kotlinx.coroutines.launch

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import com.example.kotlin_compose.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import com.example.kotlin_compose.presentation.utils.isEmpty
import com.example.kotlin_compose.ui.theme.Green31
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

val listButton = listOf("All", "Gamelists", "Articles", "Videos")
val tabs = listOf("Posts", "Saved", "Drafts")

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

    Box(modifier = Modifier.fillMaxSize()) {
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
                    modifier = Modifier.padding(vertical = 16.dp),
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier
                                .customTabIndicatorOffset(
                                    currentTabPosition = tabPositions[pagerState.currentPage],
                                    tabWidth = tabWidths[pagerState.currentPage]
                                )
                                .clip(shape = RoundedCornerShape(10.dp)), color = Green31
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
        color = Color.White, fontFamily = PPNeu, fontWeight = FontWeight.Bold
    )
    HorizontalPager(
        state = pagerState, beyondBoundsPageCount = 10
    ) { page ->
        Column(modifier = Modifier.fillMaxSize()) {
            CustomButton(
                onPress = {},
                children = {
                    Text(
                        "All", color = Color.White, style = styleTextBtn
                    )
                },
            )
            if(isEmpty("123")){
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