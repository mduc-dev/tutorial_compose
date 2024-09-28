package com.example.kotlin_compose.presentation.screens.account

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kotlin_compose.R
import com.example.kotlin_compose.presentation.components.ButtonSize
import com.example.kotlin_compose.presentation.components.DDButton
import com.example.kotlin_compose.presentation.components.NoExistData
import com.example.kotlin_compose.presentation.components.Variant
import com.example.kotlin_compose.presentation.utils.DisabledInteractionSource
import com.example.kotlin_compose.presentation.utils.isEmpty
import com.example.kotlin_compose.ui.theme.Black1A
import com.example.kotlin_compose.ui.theme.BlackDisable
import com.example.kotlin_compose.ui.theme.BlackF3
import com.example.kotlin_compose.ui.theme.Green1a
import com.example.kotlin_compose.ui.theme.Green31
import com.example.kotlin_compose.ui.theme.PPNeu
import kotlinx.coroutines.launch

val tabs = listOf("Posts", "Saved", "Drafts")
val enumValuesChip = listOf("All", "Gamelists", "Articles", "Videos")

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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

    Scaffold(containerColor = Color.Black, topBar = {
        TopAppBar(title = { }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        ), navigationIcon = {
            Column(modifier = Modifier
                .padding(horizontal = 8.dp),
                horizontalAlignment = CenterHorizontally
            ){
                AsyncImage(
                    model = "https://ui-avatars.com/api/?name=D&bold=true&background=ab47bc&color=ffff&size=128",
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp).clip(CircleShape)
                )
            }

        }, actions = {
            Row {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.qr_code),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .width(22.dp)
                            .height(22.dp)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .width(22.dp)
                            .height(22.dp)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.settings),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .width(22.dp)
                            .height(22.dp)
                    )
                }
            }
        })
    }) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding()),
            horizontalAlignment = CenterHorizontally,
        ) {
            item {
                //TODO: Header Account
                HeaderAccount()
                //Tabs
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.Transparent,
                    divider = {},
                    modifier = Modifier.padding(top = 16.dp),
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
                            modifier = Modifier.padding(top = 50.dp),
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
                                    .padding(bottom = 10.dp),
                                onTextLayout = { textLayoutResult ->
                                    tabWidths[tabIndex] =
                                        with(density) { textLayoutResult.size.width.toDp() }
                                })
                        }
                    }
                }
                //Content Tab
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
        state = pagerState
    ) { page ->
        when (page) {
            0, 1 -> Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp, vertical = 5.dp),
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
                Content()
            }

            2 -> {
                NoExistData(
                    subTextNull = "Write a post to start your profile’s never-ending journey.",
                    painterResourceName = R.drawable.sad_icon_top,
                )
            }
        }

    }
}


@Composable
fun HeaderAccount() {
    AsyncImage(
        model = "https://ui-avatars.com/api/?name=D&bold=true&background=ab47bc&color=ffff&size=512",
        contentDescription = null
    )
}


@Composable
fun Content() {
    if (isEmpty("null")) {
        NoExistData(
            subTextNull = "Write a post to start your profile’s never-ending journey.",
            painterResourceName = R.drawable.sad_icon_top,
        )
    } else {
        DDButton(
            isLoading = true,
            label = "hello",
            onPress = {},
            size = ButtonSize.LG,
            variant = Variant.BORDERED
        )
    }

}

//fun LazyListScope.header() {
//    items(100) {
//        Text(text = "hello world")
//    }
//}

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


@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
fun AccountPreview() {
    Account()
}