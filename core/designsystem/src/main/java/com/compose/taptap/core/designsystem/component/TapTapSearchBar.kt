package com.compose.taptap.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.theme.IntlV2Grey80
import com.compose.taptap.core.designsystem.theme.IntlV2Grey90
import com.compose.taptap.core.designsystem.theme.PPNeu

@Composable
fun TapTapSearchBar(
    placeholderText: String,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(32.dp)
            .background(
                color = IntlV2Grey90, shape = RoundedCornerShape(18.dp)
            )
            .clickable(onClick = onSearchClick),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.cw_toolbar_search_ic),
            contentDescription = "Search",
            tint = IntlV2Grey80,
            modifier = Modifier
                .padding(start = 6.dp)
                .size(24.dp)
        )
        Text(
            text = placeholderText,
            color = IntlV2Grey80,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            fontFamily = PPNeu,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
