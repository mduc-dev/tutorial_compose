package com.compose.taptap.core.designsystem.component.molecules.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapTextVariant
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapTapListItem(
    headline: String,
    supportingText: String? = null,
    textColor: Color = TapTapTheme.colors.onSurface,
    trailingContent: @Composable (() -> Unit)? = null,
    showTrailingIcon: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TapTapText(
                text = headline,
                style = TapTapTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
            if (supportingText != null) {
                TapTapText(
                    text = supportingText,
                    variant = TapTapTextVariant.SM,
                    style = TapTapTheme.typography.bodyMedium,
                    color = TapTapTheme.colors.onSurfaceVariant
                )
            }
        }
        if (trailingContent != null) {
            trailingContent()
        } else if (showTrailingIcon) {
//             Icon(
//                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                contentDescription = null,
//                tint = TapTapTheme.colors.onSurfaceVariant,
//                modifier = Modifier.size(24.dp)
//            )
        }
    }
}
