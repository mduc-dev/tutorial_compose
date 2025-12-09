package com.compose.taptap.feature.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.component.molecules.settings.TapTapListItem
import com.compose.taptap.core.designsystem.component.organisms.dialog.LogoutConfirmDialog
import com.compose.taptap.core.designsystem.component.templates.MainScreenTemplate
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.theme.WhitePrimary
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.core.navigation.currentComposeNavigator
import com.compose.taptap.core.preview.TapTapPreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
data class SettingsItem(
    val title: String,
    val destination: TapTapScreen? = null,
    val isLogout: Boolean = false
)

@Composable
fun SettingsScreen(
    onLogout: () -> Unit
) {
    val composeNavigator = currentComposeNavigator
    val showLogoutDialog = remember { mutableStateOf(false) }

    val settingsItems = remember {
        listOf(
            SettingsItem("Feedback", TapTapScreen.Feedback),
            SettingsItem("Account and Security", TapTapScreen.AccountSecurity),
            SettingsItem("Dark mode", TapTapScreen.DarkMode),
            SettingsItem("Languages", TapTapScreen.Languages),
            SettingsItem("Orders & Payments", TapTapScreen.OrdersPayments),
            SettingsItem("General", TapTapScreen.General),
            SettingsItem("Redeem", TapTapScreen.Redeem),
            SettingsItem("Download & Install", TapTapScreen.DownloadInstall),
            SettingsItem("Game Update", TapTapScreen.GameUpdate),
            SettingsItem("Notification settings", TapTapScreen.NotificationSettings),
            SettingsItem("Ver 1.0.0-marketFull.100000", TapTapScreen.InAppUpdate),
            SettingsItem("About TapTap", TapTapScreen.AboutTapTap),
            SettingsItem("Terms of Service", TapTapScreen.TermsOfService),
            SettingsItem("Privacy", TapTapScreen.Privacy),
            SettingsItem("Privacy Policy", TapTapScreen.PrivacyPolicy),
            SettingsItem("Authorization", TapTapScreen.Authorization),
            SettingsItem("Log Out", isLogout = true)
        )
    }

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
                TapTapListItem(
                    headline = item.title,
                    onClick = {
                        if (item.isLogout) {
                            showLogoutDialog.value = true
                        } else {
                            item.destination?.let { composeNavigator.navigate(it) }
                        }
                    },
                    textColor = if (item.isLogout) TapTapTheme.colors.primary else WhitePrimary,
                    showTrailingIcon = !item.isLogout
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
