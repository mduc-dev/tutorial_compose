package com.compose.taptap.feature.me

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.theme.Black1A
import com.compose.taptap.core.designsystem.theme.BlackDisable
import com.compose.taptap.core.designsystem.theme.BlackF3
import com.compose.taptap.core.designsystem.theme.Green1A
import com.compose.taptap.core.designsystem.theme.IntlCcGreenPrimary
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.DisabledInteractionSource
import com.compose.taptap.core.model.UserProfileData
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.core.navigation.currentComposeNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

val tabs = listOf("Posts", "Saved", "Drafts")
val enumValuesChip = listOf("All", "Gamelists", "Articles", "Videos") // Keep or update as needed

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MeScreen(
    viewModel: MeViewModel = koinViewModel()
) {
    val composeNavigator = currentComposeNavigator
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    val spacing = TapTapTheme.spacing
    var selectedFilter by remember { mutableStateOf(enumValuesChip[0]) } // String selection for snippet
    val styleTextBtn =
        TapTapTheme.typography.titleMedium.copy(
            color = TapTapTheme.colors.onSecondary,
            fontWeight = FontWeight.Bold
        )

    val uiState by viewModel.uiState.collectAsState()
    val userProfile = (uiState as? MeUiState.Success)?.data

    val avatarAlpha by remember {
        androidx.compose.runtime.derivedStateOf {
            val firstVisibleItemIndex = listState.firstVisibleItemIndex
            val firstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset
            if (firstVisibleItemIndex > 0) {
                1f
            } else {
                // Fade in over the first 100px (approx 40dp-ish)
                (firstVisibleItemScrollOffset / 200f).coerceIn(0f, 1f)
            }
        }
    }

    Scaffold(containerColor = TapTapTheme.colors.background, topBar = {
        TopAppBar(
            title = { }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TapTapTheme.colors.background
            ), navigationIcon = {
                // Scroll-to-show Avatar
                AsyncImage(
                    model = userProfile?.avatar,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(spacing.large) // 32.dp usually
                        .clip(CircleShape)
                        .alpha(avatarAlpha),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.intl_cc_24_general_user_default),
                    error = painterResource(R.drawable.intl_cc_24_general_user_default)
                )
            }, actions = {
                Row {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ico_24_profile_qr),
                            contentDescription = null,
                            modifier = Modifier.size(spacing.large),
                            tint = TapTapTheme.colors.onSecondary
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ico_24_profile_share),
                            contentDescription = null,
                            modifier = Modifier.size(spacing.large),
                            tint = TapTapTheme.colors.onSecondary
                        )
                    }
                    IconButton(onClick = { composeNavigator.navigate(TapTapScreen.Settings) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.uci_user_toolbar_settings_ic),
                            contentDescription = null,
                            modifier = Modifier.size(spacing.large),
                            tint = TapTapTheme.colors.onSecondary
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

            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TapTapTheme.colors.background)
                ) {
                    PrimaryTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        containerColor = Color.Transparent,
                        indicator = {
                            TabRowDefaults.PrimaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(
                                    pagerState.currentPage,
                                    matchContentSize = true
                                ),
                                color = IntlCcGreenPrimary,
                                height = 4.dp,
                                width = Dp.Unspecified,
                                shape = RoundedCornerShape(3.dp)
                            )
                        },
                        divider = {}
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selectedContentColor = Color.White,
                                unselectedContentColor = BlackDisable,
                                selected = pagerState.currentPage == index,
                                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                                interactionSource = DisabledInteractionSource(),
                                text = {
                                    Text(
                                        text = title,
                                        style = TapTapTheme.typography.titleMedium.copy(
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }

            item {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxSize(), // Allow Pager to take full screen height to enable scrolling past header
                    verticalAlignment = Alignment.Top
                ) { page ->
                    when (page) {
                        0 -> Column {
                            // Filter Chips
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
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

                            // Example Feed Content - Duplicated to demonstrate scroll
                            FeedItem(userProfile)
                        }

                        else -> {
                            UserCenterContentEmpty()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeedItem(userProfile: UserProfileData?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Video/Image Thumbnail
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                .background(Color.Gray) // Placeholder
        ) {
            // Mock content
            AsyncImage(
                model = "https://example.com/mock_video_thumb.jpg",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.sad_icon_top) // Just a placeholder
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "hello",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = userProfile?.avatar,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape),
                placeholder = painterResource(R.drawable.intl_cc_24_general_user_default),
                error = painterResource(R.drawable.intl_cc_24_general_user_default)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${userProfile?.name ?: "Duc Nguyen"} · 05/22",
                color = Color(0xFF888888),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.intl_cc_24_interaction_like_after),
                contentDescription = null,
                tint = Color(0xFF00CC66), // Green like tint
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "2",
                color = Color(0xFF00CC66),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun UserCenterHeader(userProfile: UserProfileData?) {

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
                    style = TapTapTheme.typography.titleMedium.copy(
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
                        style = TapTapTheme.typography.bodySmall.copy(fontSize = 12.sp),
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
            StatsItem(count = userProfile?.stats?.followingCount ?: 0, label = "Following", centered = true)
            Spacer(modifier = Modifier.width(32.dp))
            StatsItem(count = userProfile?.stats?.fansCount ?: 0, label = "Followers", centered = true)
            Spacer(modifier = Modifier.width(32.dp))
            StatsItem(
                count = userProfile?.stats?.voteupCount ?: 0,
                label = "Likes",
                centered = true
            ) // Placeholder for Likes

            Spacer(modifier = Modifier.weight(1f))

            // Edit Profile Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFF666666), RoundedCornerShape(20.dp))
                    .clickable { }
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Edit",
                    style = TapTapTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color.White
                )
            }
        }

        // Social Link (XML: social_link_info) - Placeholder
        // Bio (XML: whats_up)
        Text(
            text = userProfile?.intro ?: "Write a bio to help people discover you",
            style = TapTapTheme.typography.bodyMedium.copy(fontSize = 12.sp),
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
                    .height(135.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Game Library View (Placeholder for now, matching structure)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(135.dp)
                    .background(Color(0xFF242424), shape = TapTapShape.corners.dialog)
            ) {
                // Content for Game Library as per existing/XML
                Column(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "My games",
                        style = TapTapTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Game List Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val gameIcons = listOf(
                            "https://example.com/1.jpg",
                            "https://example.com/2.jpg",
                            "https://example.com/3.jpg",
                            "https://example.com/4.jpg",
                            "https://example.com/5.jpg",
                            "https://example.com/6.jpg",
                            "https://example.com/7.jpg",
                            "https://example.com/8.jpg",
                        )

                        // Max 5 items: 4 icons + 1 overflow if needed
                        LazyRow(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                           val maxItems = 5

                           items(maxItems) { index ->
                               if (index == maxItems - 1) {
                                   val remaining = gameIcons.size - (maxItems - 1)
                                   Box(
                                       modifier = Modifier
                                           .size(44.dp)
                                           .clip(RoundedCornerShape(10.dp)),
                                       contentAlignment = Alignment.Center
                                   ) {
                                       Text(
                                           text = "+$remaining",
                                           style = TapTapTheme.typography.titleSmall.copy(
                                               fontSize = 12.sp,
                                               fontWeight = FontWeight.Bold
                                           ),
                                           color = Color.White
                                       )
                                   }
                               } else {
                                    AsyncImage(
                                        model = null, // Mock URL
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color.Gray),
                                        contentScale = ContentScale.Crop,
                                        error = painterResource(R.drawable.intl_cc_24_bottom_bar_games_unselect)
                                    )
                               }
                           }
                        }

                        Icon(
                            painter = painterResource(id = R.drawable.ico_24_top_bars_forward_right),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(24.dp),
                            tint = Color(0xFF999999)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatsItem(
                            count = userProfile?.stats?.appWishlistCount ?: 8, // Mock default for visual check as per image requirement if null
                            label = "Wishlist",
                            centered = true
                        )
                        StatsItem(
                            count = userProfile?.stats?.playedAppCount ?: 16,
                            label = "Played",
                            centered = true
                        )
                        StatsItem(
                            count = userProfile?.stats?.playingAppCount ?: 0,
                            label = "Playing",
                            centered = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatsItem(count: Int, label: String, centered: Boolean = false) {
    Column(
        horizontalAlignment = if (centered) Alignment.CenterHorizontally else Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = count.toString(),
            style = TapTapTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Text(
            text = label,
            style = TapTapTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = Color(0xFF999999)
        )
    }
}

//color intl_v2_grey_90
@Composable
fun UserCenterBadges(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFF242424), shape = TapTapShape.corners.dialog)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Badges",
                style = TapTapTheme.typography.titleMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
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
                style = TapTapTheme.typography.bodySmall.copy(fontSize = 11.sp), // @style/intl_caption_11_regular
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
            style = TapTapTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color(0xFF999999), // @color/v3_common_gray_04
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
