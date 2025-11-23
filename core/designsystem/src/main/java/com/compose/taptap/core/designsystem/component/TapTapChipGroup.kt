package com.compose.taptap.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.taptap.core.designsystem.theme.BlackF16
import com.compose.taptap.core.designsystem.theme.IntlV2Grey90
import com.compose.taptap.core.designsystem.theme.PPNeu
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
            .padding(vertical = 4.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items, key = { i, _ -> i }) { i, title ->
            val isSelected = i == selectedIndex
            Text(
                text = title,
                modifier = Modifier
                    .background(
                        color = if (isSelected) White
                        else IntlV2Grey90, shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onItemClick(i) }
                    .padding(horizontal = 9.dp, vertical = 1.dp),
                color = if (isSelected) BlackF16 else LightGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PPNeu
            )
        }
    }
}


