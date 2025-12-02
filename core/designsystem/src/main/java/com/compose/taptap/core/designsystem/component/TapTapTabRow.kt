package com.compose.taptap.core.designsystem.component

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.DisabledInteractionSource
import kotlinx.coroutines.launch

@Composable
fun TapTapTabRow(
    tabs: List<String>,
    pagerState: PagerState,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    PrimaryScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp,
        containerColor = TapTapTheme.colors.background,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    pagerState.currentPage, matchContentSize = true
                ),
                color = TapTapTheme.colors.primary,
                height = 3.dp,
                width = Dp.Unspecified,
                shape = TapTapShape.corners.pill
            )
        },
        divider = { },
        modifier = modifier
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                    onTabClick(index)
                },
                text = {
                    Text(
                        text = title,
                        style = TapTapTheme.typography.labelLarge,
                        color = if (pagerState.currentPage == index) TapTapTheme.colors.primary else TapTapTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                },
                interactionSource = DisabledInteractionSource()
            )
        }
    }
}
