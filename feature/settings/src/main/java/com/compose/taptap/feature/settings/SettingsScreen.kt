package com.compose.taptap.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.theme.WhitePrimary
import com.compose.taptap.core.preview.TapTapPreviewTheme
import com.compose.taptap.core.designsystem.component.templates.MainScreenTemplate
import com.compose.taptap.core.navigation.currentComposeNavigator
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.core.designsystem.component.molecules.settings.TapTapListItem
import com.compose.taptap.core.designsystem.component.organisms.dialog.LogoutConfirmDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onLogout: () -> Unit
) {
    val composeNavigator = currentComposeNavigator
    val showLogoutDialog = remember { mutableStateOf(false) }

    val settingsItems = listOf(
        "Feedback",
        "Account and Security",
        "Dark mode",
        "Languages",
        "Orders & Payments",
        "General",
        "Redeem",
        "Download & Install",
        "Game Update",
        "Notification settings",
        "Ver 1.0.0-marketFull.100000",
        "About TapTap",
        "Terms of Service",
        "Privacy",
        "Privacy Policy",
        "Authorization",
        "Log Out"
    )

    if (showLogoutDialog.value) {
        LogoutConfirmDialog(
            onDismiss = { showLogoutDialog.value = false },
            onConfirm = {
                showLogoutDialog.value = false
                onLogout()
            }
        )
    }

    MainScreenTemplate(
        title = "Settings",
        onBackClick = { composeNavigator.navigateUp() }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(settingsItems) { index, item ->
                val isLogOut = item == "Log Out"
                TapTapListItem(
                    headline = item,
                    onClick = {
                        if (isLogOut) {
                            showLogoutDialog.value = true
                        } else {
                            composeNavigator.navigate(TapTapScreen.InAppUpdate)
                        }
                    },
                    textColor = if (isLogOut) TapTapTheme.colors.primary else WhitePrimary,
                    trailingContent = if (isLogOut) { {} } else null // Default arrow is shown if trailingContent is null, logic in component is 'if null show arrow else show content'. Wait, previous logic was 'showArrow boolean'. My new logic: 'if trailingContent != null show it else show arrow'. This is wrong if I want NO arrow. 
                    // Let's fix the component logic first. 
                    // Actually, let's keep it simple for now and stick to bool or just pass null for default arrow. 
                    // If isLogOut, I want NO arrow. My new component logic: "If trailingContent != null show it, else show arrow". This means I can't hide arrow easily without passing empty lambda? 
                    // Let's refine the component in next step if needed, or pass empty lambda.
                    // Empty lambda {} will show nothing. Correct.
                )
                if (index < settingsItems.lastIndex) {
                    HorizontalDivider(
                        color = Color.DarkGray,
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingScreen() {
    TapTapPreviewTheme {
        SettingsScreen(onLogout = {})
    }
}
