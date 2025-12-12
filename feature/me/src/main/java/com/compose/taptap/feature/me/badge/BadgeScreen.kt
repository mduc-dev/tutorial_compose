package com.compose.taptap.feature.me.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapTextVariant
import com.compose.taptap.core.designsystem.component.atoms.video.StableUri
import com.compose.taptap.core.designsystem.component.atoms.video.TapTapVideoPlayer


import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.LoadingResult
import com.compose.taptap.core.model.BadgeItem
import com.compose.taptap.core.navigation.currentComposeNavigator
import kotlinx.collections.immutable.PersistentList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BadgeScreen(
    viewModel: BadgeViewModel = koinViewModel()
) {
    val navigator = currentComposeNavigator
    val badgeState by viewModel.badgeState.collectAsState()
    val selectedBadge by viewModel.selectedBadge.collectAsState()
    val badges =
        (badgeState as? LoadingResult.Success<PersistentList<BadgeItem>>)?.value ?: emptyList()

    Scaffold(
        containerColor = Color.Black,
        contentWindowInsets = WindowInsets(0, 0, 0, 0) // We handle insets manually
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // Header matching uci_badge_manage_me_tab_toolbar.xml
            BadgeHeader(
                selectedBadge = selectedBadge,
                onBackClick = { navigator.navigateUp() }
            )

            // Grid matching uci_badge_manage_me_fragment.xml (FlashRefreshListView margins)
            // margins: left 16, right 16, top 8
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(0.dp), // Using 0 spacing, padding handled in item if needed, XML implies item view has margins? 
                // Ah, uci_badge_manage_card_view has margins: left 8, right 8. 
                // In Compose Grid, spacedBy puts space BETWEEN.
                // If items have internal margin, we might not need spacedBy or need to adjust.
                // Let's use spacedBy to simulate the visual gap effectively.
                // The XML cards have 8dp L/R margins. So total 16dp spacing between content.
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(badges) { badge ->
                    BadgeGridItem(badge)
                }
            }
        }
    }
}


@Composable
fun BadgeHeader(
    selectedBadge: BadgeItem?,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val videoUri = "android.resource://${context.packageName}/${R.raw.uci_badge_anim}".toUri()
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(440.dp) // Approximate height to fit video(220) + count + selected badge
    ) {
        // Video View: uci_badge_manage_me_tab_toolbar.xml -> badge_video: 220x220, top center
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            TapTapVideoPlayer(
                uri = StableUri(videoUri),
                modifier = Modifier.size(220.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Status Bar Placeholder
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

            // Back Button: uci_badge_manage_me_tab_toolbar.xml -> btn_back
            // Margin Top 16dp (from status bar), margin start 16dp.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ico_24_top_bars_backward_left),
                    contentDescription = "Back",
                    tint = Color.Unspecified, // Icon presumably has color or white
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onBackClick)
                )
            }

            // Badge Count View: uci_badge_manage_count_view.xml
            // Margin Top 18dp from back button area.
            Spacer(modifier = Modifier.height(18.dp))
            BadgeCountView(count = 0) // Mock count 0

            // Selected Badge Cover: uci_badge_manage_me_tab_toolbar.xml -> img_select_badge_cover
            // 92x92, top 20dp from count view.
            Spacer(modifier = Modifier.height(20.dp))

            if (selectedBadge != null) {
                AsyncImage(
                    model = selectedBadge.largeImage?.url,
                    contentDescription = null,
                    modifier = Modifier.size(92.dp)
                )

                // Selected Badge Name: uci_badge_manage_me_tab_toolbar.xml -> tv_select_badge_name
                // top 8dp, white_primary, body_16_regular
                Spacer(modifier = Modifier.height(8.dp))
                TapTapText(
                    text = selectedBadge.title ?: "",
                    color = Color.White,
                    style = TapTapTheme.typography.bodyLarge, // approx 16sp
                    textAlign = TextAlign.Center
                )

                // Equipped Tag: uci_badge_manage_me_tab_toolbar.xml -> equipped_tag
                // top 12dp, bottom 12dp
                Spacer(modifier = Modifier.height(12.dp))
                if (selectedBadge.isWear == true) { // Or if we just want to show it for the selected one if strictly following mockup
                    // Using a simple box for the tag style "@style/Tag.Primary.Rectangle.Black10Solid"
                    Box(
                        modifier = Modifier
                            .background(
                                Color(0xFFFF0000),
                                RoundedCornerShape(2.dp)
                            ) // Mocking primary tag color
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        TapTapText(
                            text = "Equipped",
                            color = Color.White,
                            variant = TapTapTextVariant.XS
                        )
                    }
                } else {
                    // Placeholder for consistency if needed, or nothing
                    Spacer(modifier = Modifier.height(20.dp)) // spacing to bottom
                }
                Spacer(modifier = Modifier.height(12.dp))
            } else {
                // Placeholder size if no badge selected?
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
    }
}

@Composable
fun BadgeCountView(count: Int) {
    // uci_badge_manage_count_view.xml
    // Structure:
    // Left Wing (19x36) - Title (18 bold) - Number (40sp) - Right Wing (19x36)
    // Center wing (42 width) below title?
    // Let's implement row and specific alignments

    Box(contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Left Wing
                AsyncImage(
                    model = R.drawable.uci_badge_count_left,
                    contentDescription = null,
                    modifier = Modifier.size(width = 19.dp, height = 36.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Title
                TapTapText(
                    text = "Badges",
                    color = Color.White.copy(alpha = 0.6f), // black_opacity60 on black bg? assuming white 60%
                    fontWeight = FontWeight.Bold,
                    style = TapTapTheme.typography.titleMedium // approx 18sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Number
                TapTapText(
                    text = count.toString(),
                    color = Color.White,
                    style = TapTapTheme.typography.displayMedium, // approx 40sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Right Wing
                AsyncImage(
                    model = R.drawable.uci_badge_count_right,
                    contentDescription = null,
                    modifier = Modifier.size(width = 19.dp, height = 36.dp)
                )
            }
            // Center Wing below? XML says "layout_marginTop=8dp" below title.
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = R.drawable.uci_badge_count_center,
                contentDescription = null,
                modifier = Modifier.width(42.dp)
            )
        }
    }
}


@Composable
fun BadgeGridItem(badge: BadgeItem) {
    // uci_badge_manage_card_view.xml
    // width wrap content, but in grid it fills cell.
    // img_badge_cover: ratio 1:1, margin 8dp
    // tv_badge_name: margin 8 horizontal, 6 top. Lines 3. body_14_regular, black_opacity60.

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp) // simulate item margins
    ) {
        AsyncImage(
            model = badge.largeImage?.url,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.height(6.dp))

        TapTapText(
            text = badge.title ?: "",
            style = TapTapTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = Color.White.copy(alpha = 0.6f),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

