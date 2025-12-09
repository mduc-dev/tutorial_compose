package com.compose.taptap.feature.me

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.button.ButtonSize
import com.compose.taptap.core.designsystem.component.atoms.button.DDButton
import com.compose.taptap.core.designsystem.component.atoms.button.Variant
import com.compose.taptap.core.designsystem.theme.Black1A
import com.compose.taptap.core.designsystem.theme.BlackDisable
import com.compose.taptap.core.designsystem.theme.BlackF3
import com.compose.taptap.core.designsystem.theme.Green1A
import com.compose.taptap.core.designsystem.theme.IntlCcGreenPrimary
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FilterSpacing
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FilterVerticalPadding
import com.compose.taptap.core.designsystem.theme.TapTapDimens.TabBottomPadding
import com.compose.taptap.core.designsystem.theme.TapTapDimens.TabTopPadding
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.isEmpty
import com.compose.taptap.core.model.UserProfileData
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.core.navigation.currentComposeNavigator
import com.compose.taptap.feature.auth.welcome.LocalWelcomeViewModel
import com.compose.taptap.feature.auth.welcome.WelcomeEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

val tabs = listOf("Posts", "Saved", "Drafts")
val enumValuesChip = listOf("All", "Gamelists", "Articles", "Videos") // Keep or update as needed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeScreen(
    viewModel: MeViewModel = koinViewModel()
) {
    val composeNavigator = currentComposeNavigator
    val welcomeViewModel = LocalWelcomeViewModel.current
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val density = LocalDensity.current
    val spacing = TapTapTheme.spacing
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }

    val uiState by viewModel.uiState.collectAsState()
    val userProfile = (uiState as? MeUiState.Success)?.data

    Scaffold(containerColor = TapTapTheme.colors.background, topBar = {
        TopAppBar(
            title = { }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TapTapTheme.colors.background
            ), navigationIcon = {
                // Empty or Back if needed, but this is a main tab usually
            }, actions = {
                Row {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ico_24_profile_qr),
                            contentDescription = null,
                            modifier = Modifier.size(spacing.large)
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ico_24_profile_share),
                            contentDescription = null,
                            modifier = Modifier.size(spacing.large)
                        )
                    }
                    IconButton(onClick = { composeNavigator.navigate(TapTapScreen.Settings) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.uci_user_toolbar_settings_ic),
                            contentDescription = null,
                            modifier = Modifier.size(spacing.large)
                        )
                    }
                }
            })
    }) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(TapTapTheme.colors.background)
                .padding(innerPadding),
            horizontalAlignment = CenterHorizontally,
        ) {
            item {
                UserCenterHeader(userProfile = userProfile)
            }

            item {
                PrimaryTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = TapTapTheme.colors.background,
                    indicator = {
                        TabRowDefaults.PrimaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(pagerState.currentPage),
                            color = IntlCcGreenPrimary,
                            height = 2.dp
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            modifier = Modifier
                                .height(40.dp)
                                .padding(top = TabTopPadding, bottom = TabBottomPadding),
                            text = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    ),
                                    color = if (pagerState.currentPage == index) IntlCcGreenPrimary else BlackDisable
                                )
                            }
                        )
                    }
                }
            }

            item {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp) // Placeholder height
                ) { page ->
                    when (page) {
                        0, 1 -> Column {
                            // My games section is now part of the header
                            // MyGamesSection() <- Removed

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = spacing.medium,
                                        end = spacing.medium,
                                        top = spacing.medium,
                                        bottom = spacing.small
                                    ),
                                horizontalArrangement = Arrangement.spacedBy(FilterSpacing)
                            ) {
                                enumValuesChip.forEach { chipText ->
                                    FilterChip(
                                        selected = false, // Implement selection logic
                                        onClick = { /* Handle chip click */ },
                                        label = {
                                            Text(
                                                text = chipText,
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Medium
                                                ),
                                                color = Black1A
                                            )
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            containerColor = BlackF3,
                                            labelColor = Black1A,
                                            selectedContainerColor = Green1A,
                                            selectedLabelColor = Color.White
                                        ),
                                        border = null,
                                        modifier = Modifier.height(FilterVerticalPadding)
                                    )
                                }
                            }
                            Content(onSignOut = { welcomeViewModel.onEvent(WelcomeEvent.OnSignOut) })
                        }

                        2 -> {
                            UserCenterContentEmpty()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserCenterHeader(userProfile: UserProfileData?) {
    val spacing = TapTapTheme.spacing

    Column(modifier = Modifier.fillMaxWidth()) {
        // Banner (XML h=0dp, seemingly invisible or collapsed initially)
        // User Icon & Name Area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    start = 16.dp,
                    end = 16.dp
                ), // Approx top margin from XML (88dp includes toolbar usually, here we are inside scaffold content)
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User Portrait
            AsyncImage(
                model = userProfile?.avatar,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp) // XML: 60dp
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.intl_cc_24_general_user_default),
                error = painterResource(R.drawable.intl_cc_24_general_user_default)
            )

            // Name & Info
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = userProfile?.name ?: "TapUser",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ), // style="@style/intl_heading_16_bold"
                    color = Color.White,
                    maxLines = 2
                )

                // ID / Identity (Combined logic from XML)
                // Assuming ID is primary "identity" to show
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.uci_user_id_card_ic),
                        contentDescription = "user_id",
                        modifier = Modifier.size(16.dp)
                    )
                    // Placeholder for ID logic if needed, or just show text
                    Text(
                        text = "ID: ${userProfile?.id ?: ""}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = Color(0xFF999999) // @color/intl_v2_grey_40
                    )
                }
            }
        }

        // Action Buttons (Edit Profile) - XML has it separate/constrained. 
        // Placing it here for cleaner Compose flow or use ConstraintLayout if strictly needed.
        // XML: edit_profile aligned to user_following (below icon).

        // Stats Row (Following, Followers, Likes)
        // XML: layout="@layout/uci_user_fans_single_item_layout"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatsItem(count = userProfile?.stats?.followingCount ?: 0, label = "Following")
            Spacer(modifier = Modifier.width(32.dp))
            StatsItem(count = userProfile?.stats?.fansCount ?: 0, label = "Followers")
            Spacer(modifier = Modifier.width(32.dp))
            StatsItem(
                count = userProfile?.stats?.voteupCount ?: 0,
                label = "Likes"
            ) // Placeholder for Likes

            Spacer(modifier = Modifier.weight(1f))

            // Edit Profile Button
            DDButton(
                label = "Edit",
                onPress = {},
                size = ButtonSize.SM, // Small
                variant = Variant.BORDERED,
                modifier = Modifier.height(28.dp) // XML padding implies small button
            )
        }

        // Social Link (XML: social_link_info) - Placeholder
        // Bio (XML: whats_up)
        Text(
            text = userProfile?.intro ?: "Write a bio to help people discover you",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color(0xFFCCCCCC), // @color/intl_v2_grey_20
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth(),
            maxLines = 2
        )

        // Game Library & Badges Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp)
        ) {
            // Badges
            UserCenterBadges(
                modifier = Modifier
                    .width(90.dp)
                    .height(130.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Game Library View (Placeholder for now, matching structure)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(130.dp)
                    .background(Color(0xFF242424), shape = MaterialTheme.shapes.medium)
            ) {
                // Content for Game Library as per existing/XML
                Column(Modifier.padding(12.dp)) {
                    Text(
                        "My games",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatsItem(
                            count = userProfile?.stats?.appWishlistCount ?: 0,
                            label = "Wishlist",
                            centered = true
                        )
                        StatsItem(
                            count = userProfile?.stats?.playedAppCount ?: 0,
                            label = "Played",
                            centered = true
                        )
                        StatsItem(
                            count = userProfile?.stats?.playingAppCount ?: 0,
                            label = "Playing",
                            centered = true
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ico_24_top_bars_forward_right),
                        contentDescription = null,
                        modifier = Modifier.size(spacing.large)
                    )
                }
            }
        }
    }
}

@Composable
fun StatsItem(count: Int, label: String, centered: Boolean = false) {
    Column(horizontalAlignment = if (centered) Alignment.CenterHorizontally else Alignment.Start) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = Color.White
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = Color(0xFF999999)
        )
    }
}

//color intl_v2_grey_90
@Composable
fun UserCenterBadges(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFF242424), shape = MaterialTheme.shapes.medium)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Badges",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Empty Badge Icon
            Image(
                painter = painterResource(id = R.drawable.uci_user_center_empty_badge),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Earn badges text
            Text(
                text = "Earn badges",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), // @style/intl_caption_11_regular
                color = Color(0xFF999999),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun UserCenterContentEmpty() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.sad_icon_top), // Placeholder for uci_user_content_empty_ic
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(width = 150.dp, height = 135.dp)
        )
        Text(
            text = "There is nothing here...", // @string/uci_user_center_bottom_empty
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color(0xFF999999), // @color/v3_common_gray_04
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

//fun LazyListScope.header() {
//    items(100) {
//        Text(text = "hello world")
//    }
//}

@Composable
fun Content(onSignOut: () -> Unit) {
    if (isEmpty("null")) {
        UserCenterContentEmpty()
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
            modifier = Modifier.clickable(onClick = onSignOut),
            color = TapTapTheme.colors.primary
        )
    }
}
