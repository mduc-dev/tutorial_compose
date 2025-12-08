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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.compose.taptap.core.designsystem.component.atoms.divider.TapTapDivider
import com.compose.taptap.core.designsystem.component.molecules.tabs.TapHomeBottomFlipView
import com.compose.taptap.core.designsystem.theme.BlackF16
import com.compose.taptap.core.designsystem.theme.IntlCcDivider
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.theme.WhitePrimary
import com.compose.taptap.core.designsystem.util.BOTTOM_TAB
import com.compose.taptap.core.designsystem.util.DisabledInteractionSource
import com.compose.taptap.core.navigation.TapTapScreen
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarHeight
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarIconSize
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarItemHeight
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarItemMarginTop
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FlipViewSize
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FlipViewTranslationY

@Composable
fun TapBottomTab(
    modifier: Modifier,
    currentRoute: TapTapScreen?,
    isFlipped: Boolean,
    flipBackImageUrl: String?,
    onFlip: () -> Unit,
    onItemClick: (TapTapScreen) -> Unit,
) {
    // List of items, with null for the middle spacer
    val firstHalf = BOTTOM_TAB.take(2)
    val secondHalf = BOTTOM_TAB.drop(2)
    val tabItems = remember { firstHalf + null + secondHalf }

    Box(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars) // Handle safe area for system nav bar
            .height(BottomBarHeight) // Explicit height
            .background(BlackF16) // @color/intl_cc_black_background
    ) {
        // Divider at the top
        TapTapDivider(
            modifier = Modifier.align(Alignment.TopCenter),
            thickness = 1.dp, // 1.0px in XML, using 1.dp for visibility or Dp.Hairline
            color = IntlCcDivider
        )

        // Row of items
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) {
            tabItems.forEach { item ->
                if (item == null) {
                    // Spacer for the center button (weight 1f to match equitable distribution)
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
                            ) { onItemClick(item.route) },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(top = BottomBarItemMarginTop),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                painter = if (currentRoute == item.route) item.selectedIcon else item.icon,
                                contentDescription = item.title,
                                tint = if (item.title == "You") WhitePrimary else Unspecified,
                                modifier = Modifier.size(BottomBarIconSize) // Standard icon size
                            )
                            // Text label if needed, XML items are "TapHomeBottomBarNormalItem" which usually have text.
                            // Assuming default text style 
                            Text(
                                text = item.title,
                                color = if (currentRoute == item.route) MaterialTheme.colorScheme.primary else Gray,
                                style = MaterialTheme.typography.labelMedium
                            )
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
                .size(FlipViewSize) // android:layout_width="60.0dip"
                .zIndex(1f),
            isFlipped = isFlipped,
            backImageUrl = flipBackImageUrl,
            onFlip = onFlip
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
            onItemClick = {}
        )
    }
}
