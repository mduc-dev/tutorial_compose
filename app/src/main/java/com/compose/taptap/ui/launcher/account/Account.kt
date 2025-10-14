package com.compose.taptap.ui.launcher.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.compose.taptap.R
import com.compose.taptap.ui.components.ButtonSize
import com.compose.taptap.ui.components.DDButton
import com.compose.taptap.ui.components.NoExistData
import com.compose.taptap.ui.components.Variant
import com.compose.taptap.ui.launcher.welcome.LocalWelcomeViewModel
import com.compose.taptap.ui.launcher.welcome.WelcomeViewModel
import com.compose.taptap.ui.theme.Black
import com.compose.taptap.ui.theme.Black1A
import com.compose.taptap.ui.theme.BlackDisable
import com.compose.taptap.ui.theme.BlackF3
import com.compose.taptap.ui.theme.Green1A
import com.compose.taptap.ui.theme.IntlCcGreenPrimary
import com.compose.taptap.ui.theme.PPNeu
import com.compose.taptap.ui.utils.DisabledInteractionSource
import com.compose.taptap.ui.utils.isEmpty
import kotlinx.coroutines.launch

val tabs = listOf("Posts", "Saved", "Drafts")
val enumValuesChip = listOf("All", "Gamelists", "Articles", "Videos")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account(
    viewModel: WelcomeViewModel = LocalWelcomeViewModel.current
) {
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
        TopAppBar(
            title = { }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        ), navigationIcon = {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                AsyncImage(
                    model = "https://ui-avatars.com/api/?name=D&bold=true&background=ab47bc&color=ffff&size=128",
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(CircleShape)
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
                .fillMaxSize().background(Black),
            horizontalAlignment = CenterHorizontally,
        ) {
            item {
                //TODO: Header Account
                HeaderAccount()
                //Tabs
                PrimaryTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.Transparent,
                    divider = {},
                    modifier = Modifier.padding(top = 16.dp),
                    indicator = {
                        TabRowDefaults.PrimaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(pagerState.currentPage),
                            width = tabWidths[pagerState.currentPage],
                            color = IntlCcGreenPrimary
                        )
                    },
                ) {
                    tabs.forEachIndexed { tabIndex, item ->
                        Tab(
                            selectedContentColor = Color.White,
                            unselectedContentColor = BlackDisable,
                            selected = tabIndex == pagerState.currentPage,
                            interactionSource = DisabledInteractionSource(),
                            modifier = Modifier.padding(top = 50.dp),
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(tabIndex)
                                }
                            }) {
                            Text(
                                text = item,
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
                PageContent(pagerState, viewModel)
            }
        }
    }
}

@Composable
fun PageContent(
    pagerState: PagerState,
    viewModel: WelcomeViewModel
) {
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
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 10.dp, alignment = CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
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
                                selectedContainerColor = Green1A,
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
                Content(viewModel)
            }

            2 -> {
                NoExistData(
                    subTextNull = "Write a post to start your profile’s never-ending journey.",
                    painterResourceName = R.drawable.sad_icon_top,
                    modifier = Modifier.padding(start = 20.dp)
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
fun Content(viewModel: WelcomeViewModel) {
    if (isEmpty("null")) {
        NoExistData(
            subTextNull = "Write a post to start your profile’s never-ending journey.",
            painterResourceName = R.drawable.sad_icon_top,
            modifier = Modifier
        )
    } else {
        DDButton(
            isLoading = true,
            label = "hello",
            onPress = {},
            size = ButtonSize.LG,
            variant = Variant.BORDERED
        )
        Text(
            "logout",
            modifier = Modifier.clickable(onClick = { viewModel.signOut() }),
            color = Color.White
        )
    }

}

//fun LazyListScope.header() {
//    items(100) {
//        Text(text = "hello world")
//    }
//}
