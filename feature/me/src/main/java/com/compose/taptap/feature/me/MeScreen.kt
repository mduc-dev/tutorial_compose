package com.compose.taptap.feature.me

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapTextVariant
import com.compose.taptap.core.designsystem.component.atoms.video.VideoCacheManager
import com.compose.taptap.core.designsystem.component.molecules.state.TapErrorView
import com.compose.taptap.core.designsystem.theme.Black1A
import com.compose.taptap.core.designsystem.theme.BlackDisable
import com.compose.taptap.core.designsystem.theme.BlackF3
import com.compose.taptap.core.designsystem.theme.Green1A
import com.compose.taptap.core.designsystem.theme.IntlCcGreenPrimary
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.DisabledInteractionSource
import com.compose.taptap.core.designsystem.util.LoadingResult
import com.compose.taptap.core.model.BadgeWearInfoData
import com.compose.taptap.core.model.FeedItem
import com.compose.taptap.core.model.UserAppStatusItem
import com.compose.taptap.core.model.UserProfileData
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.core.navigation.currentComposeNavigator
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MeScreen(
    viewModel: MeViewModel = koinViewModel()
) {
    val composeNavigator = currentComposeNavigator
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabYou.size })

    val spacing = TapTapTheme.spacing
    val context = LocalContext.current
    
    // Preload Badge Screen video to ensure instant playback when navigating
    LaunchedEffect(Unit) {
        val badgeVideoUri = "android.resource://${context.packageName}/${R.raw.uci_badge_anim}".toUri()
        // VideoCacheManager is in core.designsystem
       VideoCacheManager.preloadVideo(context, badgeVideoUri)
    }

    var selectedFilter by remember { mutableStateOf(chipYou[0]) } // String selection for snippet
    val styleTextBtn = TapTapTheme.typography.titleMedium.copy(
        color = TapTapTheme.colors.onSecondary, fontWeight = FontWeight.Bold
    )

    val uiState by viewModel.uiState.collectAsState()
    val userProfileState by viewModel.userProfileState.collectAsState(initial = LoadingResult.Loading)
    val userProfile = (userProfileState as? LoadingResult.Success)?.value

    val avatarAlpha by remember {
        derivedStateOf {
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
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateStartPadding(LayoutDirection.Ltr)
                )
                .testTag("me_list"),
            horizontalAlignment = CenterHorizontally,
        ) {
            item {
                UserCenterHeader(userProfile = userProfile)
            }

            stickyHeader {
                Column(
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
                                    pagerState.currentPage, matchContentSize = true
                                ),
                                color = IntlCcGreenPrimary,
                                height = 4.dp,
                                width = Dp.Unspecified,
                                shape = RoundedCornerShape(3.dp)
                            )
                        },
                        divider = {}) {
                        tabYou.forEachIndexed { index, title ->
                            Tab(
                                selectedContentColor = Color.White,
                                unselectedContentColor = BlackDisable,
                                selected = pagerState.currentPage == index,
                                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                                interactionSource = remember { DisabledInteractionSource() },
                                text = {
                                    TapTapText(
                                        text = title,
                                        style = TapTapTheme.typography.titleMedium.copy(
                                            fontSize = 18.sp,
                                        ),
                                        fontWeight = FontWeight.Bold
                                    )
                                })
                        }
                    }

                    AnimatedVisibility(
                        visible = pagerState.currentPage == 0 || pagerState.currentPage == 1,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(chipYou) { chipText ->
                                FilterChip(
                                    selected = chipText == selectedFilter,
                                    onClick = {
                                        selectedFilter = chipText
                                    },
                                    label = { TapTapText(text = chipText, style = styleTextBtn) },
                                    interactionSource = remember { DisabledInteractionSource() },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Green1A,
                                        labelColor = BlackDisable,
                                        selectedLabelColor = Color.White,
                                        containerColor = Black1A,
                                    ),
                                    border = FilterChipDefaults.filterChipBorder(
                                        enabled = true,
                                        selected = chipText == selectedFilter,
                                        borderColor = BlackF3,
                                        selectedBorderColor = Color.Transparent,
                                        selectedBorderWidth = 0.dp,
                                    ),
                                    shape = MaterialTheme.shapes.small
                                )
                            }
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
                    val contentModifier = Modifier
                        .fillMaxWidth()
                        .stickyHeaderOffset(listState, page)
                    when (page) {
                        0 -> {
                            // Posts
                            if (uiState.posts.isNotEmpty()) {
                                LazyColumn(
                                    modifier = contentModifier,
                                    contentPadding = PaddingValues(bottom = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(uiState.posts, key = { it.post?.id ?: 0 }) { item ->
                                        FeedItem(item = item)
                                    }
                                }
                            } else {
                                TapErrorView(
                                    modifier = contentModifier,
                                    icon = R.drawable.emoji_3d_lilac_front_sad,
                                    title = "Emptier than the void",
                                    subTitle = "Looks like you have scribed all of your thoughts.",
                                    buttonText = "Retry",
                                    onRetry = {}
                                )
                            }
                        }

                        1 -> {
                            // Saved
                            TapErrorView(
                                modifier = contentModifier,
                                icon = R.drawable.emoji_3d_lilac_righttop_sad,
                                title = "Emptier than the void",
                                subTitle = "Save your favorite content to populate your profile's never-ending journey.",
                                buttonText = "Retry",
                                onRetry = {}
                            )
                        }

                        2 -> {
                            // Drafts
                            TapErrorView(
                                modifier = contentModifier,
                                icon = R.drawable.emoji_3d_lilac_rightbottom_sad,
                                title = "Emptier than the void",
                                subTitle = "Looks like you have scribed all of your thoughts.",
                                buttonText = "Retry",
                                onRetry = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeedItem(item: FeedItem) {
    val post = item.post ?: return
    val user = post.user

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
                model = post.listFields?.cover?.url,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.sad_icon_top),
                error = painterResource(R.drawable.sad_icon_top)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TapTapText(
            text = post.listFields?.title ?: "",
            color = TapTapTheme.colors.onSurface,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = user?.avatar,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape),
                placeholder = painterResource(R.drawable.intl_cc_24_general_user_default),
                error = painterResource(R.drawable.intl_cc_24_general_user_default)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TapTapText(
                text = "${user?.name ?: "TapUser"} · 05/23/24",
                variant = TapTapTextVariant.XS,
                color = TapTapTheme.colors.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.intl_cc_24_interaction_like_after),
                contentDescription = null,
                tint = Color(0xFF00CC66), // Green like tint
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            TapTapText(
                text = "${post.stat?.ups ?: 0}", variant = TapTapTextVariant.XS, color = TapTapTheme.colors.primary
            )
        }
    }
}

@Composable
fun UserCenterHeader(
    userProfile: UserProfileData?,
    viewModel: MeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        // Banner (XML h=0dp, seemingly invisible or collapsed initially)
        // User Icon & Name Area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp, start = 16.dp, end = 16.dp
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
                TapTapText(
                    text = userProfile?.name ?: "TapUser",
                    style = TapTapTheme.typography.titleMedium, // style="@style/intl_heading_16_bold"
                    fontWeight = FontWeight.Bold,
                    color = TapTapTheme.colors.onSurface,
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
                    TapTapText(
                        text = "ID: ${userProfile?.id ?: ""}",
                        variant = TapTapTextVariant.XS,
                        color = TapTapTheme.colors.textGray // @color/intl_v2_grey_40
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
            StatsItem(
                count = userProfile?.stats?.followingCount ?: 0,
                label = "Following",
                centered = true
            )
            Spacer(modifier = Modifier.width(32.dp))
            StatsItem(
                count = userProfile?.stats?.fansCount ?: 0, label = "Followers", centered = true
            )
            Spacer(modifier = Modifier.width(32.dp))
            StatsItem(
                count = userProfile?.stats?.voteupCount ?: 0, label = "Likes", centered = true
            ) // Placeholder for Likes

            Spacer(modifier = Modifier.weight(1f))

            // Edit Profile Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFF666666), RoundedCornerShape(20.dp))
                    .clickable { }
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center) {
                TapTapText(
                    text = "Edit",
                    variant = TapTapTextVariant.XS,
                    fontWeight = FontWeight.Medium,
                    color = TapTapTheme.colors.onSurface
                )
            }
        }

        // Social Link (XML: social_link_info) - Placeholder
        // Bio (XML: whats_up)
        TapTapText(
            text = userProfile?.intro ?: "Write a bio to help people discover you",
            variant = TapTapTextVariant.XS,
            color = TapTapTheme.colors.onSurfaceVariant, // @color/intl_v2_grey_20
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
            val navigator = currentComposeNavigator
            UserCenterBadges(
                badges = uiState.badges,
                modifier = Modifier
                    .width(90.dp)
                    .height(135.dp)
                    .clickable { navigator.navigate(TapTapScreen.Badge) }
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Game Library View
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(135.dp)
                    .background(Color(0xFF242424), shape = TapTapShape.corners.dialog)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
                ) {
                    TapTapText(
                        text = "My games",
                        fontWeight = FontWeight.Bold,
                        color = TapTapTheme.colors.onSurface,
                    )

                    Spacer(Modifier.height(8.dp))


                    var itemWidth by remember { mutableStateOf(0.dp) }
                    val spacing = 8.dp

                    // Game List Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Max 5 items: 4 icons + 1 overflow if needed
                        // Add padding end to reduce width -> smaller items while keeping 3.66 ratio
                        GameListContent(
                            games = uiState.myGames,
                            spacing = spacing,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp),
                            onItemWidthChange = { width -> itemWidth = width }
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ico_12_general_arrow),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .size(12.dp),
                            tint = TapTapTheme.colors.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (itemWidth > 0.dp) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Wishlist: Centered on Item 1
                            Box(
                                modifier = Modifier.width(itemWidth),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    TapTapText(
                                        text = "${userProfile?.stats?.appWishlistCount ?: 8}",
                                        variant = TapTapTextVariant.XS,
                                        fontWeight = FontWeight.Bold,
                                        color = TapTapTheme.colors.onSurface
                                    )
                                    TapTapText(
                                        text = "Wishlist",
                                        variant = TapTapTextVariant.XS,
                                        style = TapTapTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                        color = TapTapTheme.colors.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }

                            // Played: Centered on Item 3
                            Box(
                                modifier = Modifier.width(itemWidth),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    TapTapText(
                                        text = "${userProfile?.stats?.playedAppCount ?: 16}",
                                        variant = TapTapTextVariant.XS,
                                        fontWeight = FontWeight.Bold,
                                        color = TapTapTheme.colors.onSurface
                                    )
                                    TapTapText(
                                        text = "Played",
                                        variant = TapTapTextVariant.XS,
                                        style = TapTapTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                        color = TapTapTheme.colors.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }

                            // Playing: Centered on Item 4
                            Box(
                                modifier = Modifier.width(itemWidth),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    TapTapText(
                                        text = "${userProfile?.stats?.playingAppCount ?: 0}",
                                        variant = TapTapTextVariant.XS,
                                        fontWeight = FontWeight.Bold,
                                        color = TapTapTheme.colors.onSurface
                                    )
                                    TapTapText(
                                        text = "Playing",
                                        variant = TapTapTextVariant.XS,
                                        style = TapTapTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                        color = TapTapTheme.colors.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
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
        TapTapText(
            text = count.toString(),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = TapTapTheme.colors.onSurface
        )
        TapTapText(
            text = label,
            variant = TapTapTextVariant.XS,
            color = TapTapTheme.colors.textGray
        )
    }
}

//color intl_v2_grey_90
@Composable
fun UserCenterBadges(
    badges: BadgeWearInfoData?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(Color(0xFF242424), shape = TapTapShape.corners.dialog)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            TapTapText(
                text = "Badges",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = TapTapTheme.colors.onSurface,
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
            TapTapText(
                text = "Earn badges",
                variant = TapTapTextVariant.XS,
                style = TapTapTheme.typography.bodySmall.copy(fontSize = 11.sp), // @style/intl_caption_11_regular
                color = TapTapTheme.colors.textGray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun GameListContent(
    games: ImmutableList<UserAppStatusItem>,
    spacing: Dp,
    modifier: Modifier = Modifier,
    onItemWidthChange: (Dp) -> Unit
) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
        modifier = modifier.onSizeChanged {
            componentWidth = with(density) { it.width.toDp() }
        }
    ) {
        // Calculate item size to show 3 items fully and 2/3 of the 4th item
        // Formula: Width = 3.66 * ItemWidth + 3 * Spacing
        // ItemWidth = (Width - 3 * Spacing) / 3.66
        val visibleItems = 3.66f

        // Avoid division by zero or negative width initially
        val itemWidth =
            if (componentWidth > 0.dp) ((componentWidth - (spacing * 3)) / visibleItems) else 0.dp

        LaunchedEffect(itemWidth) {
            onItemWidthChange(itemWidth)
        }

        // Ensure height matches width or fixed aspect ratio
        val itemHeight = itemWidth

        if (itemWidth > 0.dp) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                val displayLimit = 5
                val showOverflow = games.size > displayLimit
                // If overflow, we show 5 items + 1 overflow item (total 6).
                // If not, we show all items (size <= 5).
                val displayCount = if (showOverflow) displayLimit + 1 else games.size

                items(displayCount) { index ->
                    if (showOverflow && index == displayLimit) {
                        Box(
                            modifier = Modifier
                                .size(itemWidth, itemHeight)
                                .clip(RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${games.size} +",
                                style = TapTapTheme.typography.titleSmall.copy(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Color.White
                            )
                        }
                    } else {
                        // Regular game item
                        AsyncImage(
                            model = games[index].app?.icon?.url,
                            contentDescription = "my-games-item",
                            modifier = Modifier
                                .size(itemWidth, itemHeight)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF333333)), // Placeholder background
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.intl_cc_24_bottom_bar_games_unselect)
                        )
                    }
                }
            }
        }
    }
}

private fun Modifier.stickyHeaderOffset(listState: LazyListState, page: Int) = graphicsLayer {
    val pagerItem = listState.layoutInfo.visibleItemsInfo.find { it.index == 2 }
    val itemOffset = pagerItem?.offset ?: 0

    val stickyHeight = if (page == 0 || page == 1) 136.dp else 72.dp
    val minPadding = 24.dp

    val stickyHeightPx = stickyHeight.toPx()
    val minPaddingPx = minPadding.toPx()

    translationY = maxOf(minPaddingPx, stickyHeightPx - itemOffset)
}
