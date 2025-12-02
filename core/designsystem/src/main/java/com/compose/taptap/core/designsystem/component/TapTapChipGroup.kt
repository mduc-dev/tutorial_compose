package com.compose.taptap.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import com.compose.taptap.core.designsystem.theme.IntlV2Grey90
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.DisableParentPagerSwipeConnection

@Composable
fun TapTapChipGroup(
    items: List<String>,
    selectedIndex: Int,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .nestedScroll(DisableParentPagerSwipeConnection)
            .padding(vertical = TapTapTheme.spacing.small),
        contentPadding = PaddingValues(horizontal = TapTapTheme.spacing.mediumLarge),
        horizontalArrangement = Arrangement.spacedBy(TapTapTheme.spacing.small)
    ) {
        itemsIndexed(items, key = { i, _ -> i }) { i, title ->
            val isSelected = i == selectedIndex
            Text(
                text = title,
                modifier = Modifier
                    .background(
                        color = if (isSelected) TapTapTheme.colors.onSurface
                        else IntlV2Grey90, shape = TapTapShape.corners.medium
                    )
                    .clickable { onItemClick(i) }
                    .padding(
                        horizontal = TapTapTheme.spacing.small,
                        vertical = TapTapTheme.spacing.tiny
                    ),
                color = if (isSelected) TapTapTheme.colors.background else LightGray,
                style = TapTapTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
        }
    }
}
