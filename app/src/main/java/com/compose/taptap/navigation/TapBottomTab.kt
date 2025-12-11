package com.compose.taptap.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.taptap.core.designsystem.component.atoms.divider.TapTapDivider
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.component.molecules.tabs.MeTabIcon
import com.compose.taptap.core.designsystem.component.molecules.tabs.TapHomeBottomFlipView
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarHeight
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarIconSize
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarItemHeight
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarItemMarginTop
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FlipViewSize
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FlipViewTranslationY
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.theme.WhitePrimary
import com.compose.taptap.core.designsystem.util.BOTTOM_TAB
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.feature.me.MeUiState
import com.compose.taptap.feature.me.MeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TapBottomTab(
    modifier: Modifier,
    currentRoute: TapTapScreen?,
    isFlipped: Boolean,
    flipBackImageUrl: String?,
    onFlip: () -> Unit,
    onItemClick: (TapTapScreen) -> Unit,
) {

    val firstHalf = BOTTOM_TAB.take(2)
    val secondHalf = BOTTOM_TAB.drop(2)
    val tabItems = remember { firstHalf + null + secondHalf }

    Box(
        modifier = modifier
            .height(BottomBarHeight)
            .background(TapTapTheme.colors.scrim)
    ) {
        // Divider at the top
        TapTapDivider(
            modifier = Modifier.align(Alignment.TopCenter),
            thickness = 1.dp,
            color = TapTapTheme.colors.divider
        )

        // Row of items
        Row(
            modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Top
        ) {
            tabItems.forEach { item ->
                if (item == null) {
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    // Normal Item
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(BottomBarItemHeight + BottomBarItemMarginTop) // Space for content + margin
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null // Disable ripple if not needed/custom
                            ) { onItemClick(item.route) }, contentAlignment = Alignment.TopCenter
                    ) {
                        if (item.route == TapTapScreen.Me) {
                            val meViewModel: MeViewModel = koinViewModel()
                            val uiState by meViewModel.uiState.collectAsStateWithLifecycle()
                            val userProfile = (uiState as? MeUiState.Success)?.data
                            Box(modifier = Modifier.padding(top = BottomBarItemMarginTop)) {
                                MeTabIcon(
                                    selected = currentRoute == item.route,
                                    userProfile = userProfile
                                )
                            }
                        } else {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(top = BottomBarItemMarginTop),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                if (currentRoute == item.route) item.selectedIcon else item.icon?.let {
                                    Icon(
                                        painter = it,
                                        contentDescription = item.title,
                                        tint = if (item.title == "You") WhitePrimary else Unspecified,
                                        modifier = Modifier.size(BottomBarIconSize)
                                    )
                                }
                                TapTapText(
                                    text = item.title,
                                    color = if (currentRoute == item.route) TapTapTheme.colors.onBackground else TapTapTheme.colors.onSurfaceVariant,
                                    style = TapTapTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Flip View positioned absolutely
        TapHomeBottomFlipView(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = FlipViewTranslationY) // android:translationY="-12.0dip"
                .size(FlipViewSize), // android:layout_width="60.0dip"
            isFlipped = isFlipped, backImageUrl = flipBackImageUrl, onFlip = onFlip
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BottomTabNavigationPreview() {
    TapTapTheme(dynamicColor = false) {
        TapBottomTab(
            currentRoute = TapTapScreen.Game,
            modifier = Modifier,
            isFlipped = false,
            flipBackImageUrl = null,
            onFlip = {},
            onItemClick = {})
    }
}
