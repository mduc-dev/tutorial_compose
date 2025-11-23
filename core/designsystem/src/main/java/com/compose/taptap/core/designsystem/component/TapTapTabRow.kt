package com.compose.taptap.core.designsystem.component

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.taptap.core.designsystem.theme.BlackF16
import com.compose.taptap.core.designsystem.theme.IntlCcGreenPrimary
import com.compose.taptap.core.designsystem.theme.IntlV2Grey60
import com.compose.taptap.core.designsystem.theme.PPNeu
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
        containerColor = BlackF16,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    pagerState.currentPage, matchContentSize = true
                ),
                color = IntlCcGreenPrimary,
                height = 3.dp,
                width = Dp.Unspecified,
                shape = RoundedCornerShape(50)
            )
        },
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
                        fontFamily = PPNeu,
                        fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp,
                        color = if (pagerState.currentPage == index) White else IntlV2Grey60
                    )
                },
                interactionSource = DisabledInteractionSource()
            )
        }
    }
}
