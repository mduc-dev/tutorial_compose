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
                        when (item) {
                            "Feedback" -> composeNavigator.navigate(TapTapScreen.Feedback)
                            "Account and Security" -> composeNavigator.navigate(TapTapScreen.AccountSecurity)
                            "Dark mode" -> composeNavigator.navigate(TapTapScreen.DarkMode)
                            "Languages" -> composeNavigator.navigate(TapTapScreen.Languages)
                            "Orders & Payments" -> composeNavigator.navigate(TapTapScreen.OrdersPayments)
                            "General" -> composeNavigator.navigate(TapTapScreen.General)
                            "Redeem" -> composeNavigator.navigate(TapTapScreen.Redeem)
                            "Download & Install" -> composeNavigator.navigate(TapTapScreen.DownloadInstall)
                            "Game Update" -> composeNavigator.navigate(TapTapScreen.GameUpdate)
                            "Notification settings" -> composeNavigator.navigate(TapTapScreen.NotificationSettings)
                            "Ver 1.0.0-marketFull.100000" -> composeNavigator.navigate(TapTapScreen.InAppUpdate)
                            "About TapTap" -> composeNavigator.navigate(TapTapScreen.AboutTapTap)
                            "Terms of Service" -> composeNavigator.navigate(TapTapScreen.TermsOfService)
                            "Privacy" -> composeNavigator.navigate(TapTapScreen.Privacy)
                            "Privacy Policy" -> composeNavigator.navigate(TapTapScreen.PrivacyPolicy)
                            "Authorization" -> composeNavigator.navigate(TapTapScreen.Authorization)
                            "Log Out" -> showLogoutDialog.value = true
                            else -> {
                                // For items that don't need navigation
                            }
                        }
                    },
                    textColor = if (isLogOut) TapTapTheme.colors.primary else WhitePrimary,
                    showTrailingIcon = !isLogOut
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
