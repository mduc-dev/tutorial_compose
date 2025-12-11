package com.compose.taptap.core.designsystem.component.molecules.tabs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.theme.TapTapDimens.BottomBarIconSize
import com.compose.taptap.core.designsystem.theme.TapTapTheme

/**
 * Reusable composable for standard bottom tab items with icon and text.
 */
@Composable
fun BottomTabIcon(
    title: String,
    @DrawableRes iconRes: Int,
    @DrawableRes selectedIconRes: Int,
    selected: Boolean,
    tint: Color = Color.Unspecified
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painter = painterResource(id = if (selected) selectedIconRes else iconRes),
            contentDescription = title,
            tint = tint,
            modifier = Modifier.size(BottomBarIconSize)
        )
        TapTapText(
            text = title,
            color = if (selected) TapTapTheme.colors.onBackground else TapTapTheme.colors.onSurfaceVariant,
            style = TapTapTheme.typography.labelMedium
        )
    }
}
