package com.compose.taptap.core.designsystem.component.atoms.selection

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapTapSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = TapTapTheme.colors.onPrimary,
            checkedTrackColor = TapTapTheme.colors.primary,
            uncheckedThumbColor = TapTapTheme.colors.outline,
            uncheckedTrackColor = TapTapTheme.colors.surfaceContainerHighest,
        )
    )
}
