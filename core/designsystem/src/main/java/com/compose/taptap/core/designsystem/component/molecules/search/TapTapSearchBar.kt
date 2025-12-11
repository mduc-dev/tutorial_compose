package com.compose.taptap.core.designsystem.component.molecules.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapTextVariant
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme

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
                color = TapTapTheme.colors.surface, shape = TapTapShape.corners.pill
            )
            .clickable(onClick = onSearchClick),
        horizontalArrangement = Arrangement.spacedBy(TapTapTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.cw_toolbar_search_ic),
            contentDescription = "Search",
            tint = TapTapTheme.colors.onSurface.copy(alpha = 0.6f),
            modifier = Modifier
                .padding(start = TapTapTheme.spacing.small)
                .size(24.dp)
        )
        TapTapText(
            text = placeholderText,
            variant = TapTapTextVariant.SM,
            style = TapTapTheme.typography.bodyMedium,
            color = TapTapTheme.colors.onSurface.copy(alpha = 0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
