package com.compose.taptap.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.divider.TapTapDivider
import com.compose.taptap.core.designsystem.component.molecules.tabs.BottomTabIcon
import com.compose.taptap.core.designsystem.component.molecules.tabs.MeTabIcon
import com.compose.taptap.core.designsystem.component.molecules.tabs.TapHomeBottomFlipView
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarHeight
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarItemHeight
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarItemMarginTop
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FlipViewSize
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FlipViewTranslationY
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.BottomTab
import com.compose.taptap.core.designsystem.util.LoadingResult
import com.compose.taptap.core.navigation.TapTapScreen

import com.compose.taptap.core.model.UserProfileData

/**
 * Bottom tab items for the main navigation.
 */


@Composable
fun TapBottomTab(
    modifier: Modifier,
    currentRoute: TapTapScreen?,
    isFlipped: Boolean,
    flipBackImageUrl: String?,
    userProfile: UserProfileData? = null,
    onFlip: () -> Unit,
    onItemClick: (TapTapScreen) -> Unit,
) {

    val tabItems = remember(userProfile) {
        val allTabs = listOf(
            BottomTab(
                title = "Games",
                route = TapTapScreen.Game,
                content = { selected ->
                    BottomTabIcon(
                        title = "Games",
                        iconRes = R.drawable.cw_home_bottom_games_icon_unselect,
                        selectedIconRes = R.drawable.cw_home_bottom_games_icon_selected,
                        selected = selected
                    )
                }
            ),
            BottomTab(
                title = "Play",
                route = TapTapScreen.Play,
                content = { selected ->
                    BottomTabIcon(
                        title = "Play",
                        iconRes = R.drawable.intl_cc_24_bottom_bar_games_unselect,
                        selectedIconRes = R.drawable.intl_cc_24_bottom_bar_games_select,
                        selected = selected
                    )
                }
            ),
            BottomTab(
                title = "Tavern",
                route = TapTapScreen.Tavern,
                content = { selected ->
                    BottomTabIcon(
                        title = "Tavern",
                        iconRes = R.drawable.home_bottom_icon_tavern_unselect,
                        selectedIconRes = R.drawable.home_bottom_icon_tavern_selected,
                        selected = selected
                    )
                }
            ),
            BottomTab(
                title = "You",
                route = TapTapScreen.Me,
                content = { selected ->
                    MeTabIcon(
                        selected = selected,
                        userProfile = userProfile
                    )
                }
            ),
        )
        val firstHalf = allTabs.take(2)
        val secondHalf = allTabs.drop(2)
        firstHalf + null + secondHalf
    }

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
                    // Tab Item
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(BottomBarItemHeight + BottomBarItemMarginTop)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onItemClick(item.route) },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Box(modifier = Modifier.padding(top = BottomBarItemMarginTop)) {
                            item.content(currentRoute == item.route)
                        }
                    }
                }
            }
        }

        // Flip View positioned absolutely
        TapHomeBottomFlipView(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = FlipViewTranslationY)
                .size(FlipViewSize),
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
