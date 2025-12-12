package com.compose.taptap.navigation

import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.feature.auth.forgotpassword.ForgotPasswordScreen
import com.compose.taptap.feature.auth.login.LoginScreen
import com.compose.taptap.feature.auth.loginwithoutpassword.LoginWithoutPasswordScreen
import com.compose.taptap.feature.auth.signup.SignupScreen
import com.compose.taptap.feature.auth.welcome.WelcomeScreen
import com.compose.taptap.feature.game.GameRoute
import com.compose.taptap.feature.game_detail.GameDetail
import com.compose.taptap.feature.me.MeScreen
import com.compose.taptap.feature.me.badge.BadgeScreen
import com.compose.taptap.feature.notifications.Notifications
import com.compose.taptap.feature.play.Play
import com.compose.taptap.feature.search.SearchRoute
import com.compose.taptap.feature.settings.InAppUpdateScreen
import com.compose.taptap.feature.settings.SettingsPlaceholderScreen
import com.compose.taptap.feature.settings.SettingsScreen
import com.compose.taptap.feature.tavern.Tavern

val tapAuthEntryProvider = entryProvider<NavKey> {
    entry<TapTapScreen.Welcome> {
        WelcomeScreen()
    }
    entry<TapTapScreen.Login> {
        LoginScreen()
    }
    entry<TapTapScreen.SignUp> {
        SignupScreen()
    }
    entry<TapTapScreen.ForgotPassword> {
        ForgotPasswordScreen()
    }
    entry<TapTapScreen.LoginWithoutPassword> {
        LoginWithoutPasswordScreen()
    }
}

fun tapMainEntryProvider(
    onToggleFlip: (Boolean, String?) -> Unit
) = entryProvider<NavKey> {
    entry<TapTapScreen.Game> {
        GameRoute()
    }
    entry<TapTapScreen.Play> { Play(onToggleFlip = onToggleFlip) }
    entry<TapTapScreen.Tavern> { Tavern() }
    entry<TapTapScreen.Me> { MeScreen() }
    entry<TapTapScreen.Badge> { BadgeScreen() }
    entry<TapTapScreen.Search> {
        SearchRoute()
    }
    entry<TapTapScreen.Notifications> {
        Notifications()
    }
    entry<TapTapScreen.GameDetail> {
        GameDetail()
    }
    entry<TapTapScreen.Settings> {
        SettingsScreen(
            onLogout = {} // Placeholder for logout logic
        )
    }
    entry<TapTapScreen.InAppUpdate> {
        InAppUpdateScreen()
    }
    entry<TapTapScreen.GameUpdate> {
        SettingsPlaceholderScreen("Game Update")
    }

    // Settings Placeholders
    entry<TapTapScreen.Feedback> { SettingsPlaceholderScreen("Feedback") }
    entry<TapTapScreen.AccountSecurity> { SettingsPlaceholderScreen("Account and Security") }
    entry<TapTapScreen.DarkMode> { SettingsPlaceholderScreen("Dark mode") }
    entry<TapTapScreen.Languages> { SettingsPlaceholderScreen("Languages") }
    entry<TapTapScreen.OrdersPayments> { SettingsPlaceholderScreen("Orders & Payments") }
    entry<TapTapScreen.General> { SettingsPlaceholderScreen("General") }
    entry<TapTapScreen.Redeem> { SettingsPlaceholderScreen("Redeem") }
    entry<TapTapScreen.DownloadInstall> { SettingsPlaceholderScreen("Download & Install") }
    entry<TapTapScreen.NotificationSettings> { SettingsPlaceholderScreen("Notification settings") }
    entry<TapTapScreen.AboutTapTap> { SettingsPlaceholderScreen("About TapTap") }
    entry<TapTapScreen.TermsOfService> { SettingsPlaceholderScreen("Terms of Service") }
    entry<TapTapScreen.Privacy> { SettingsPlaceholderScreen("Privacy") }
    entry<TapTapScreen.PrivacyPolicy> { SettingsPlaceholderScreen("Privacy Policy") }
    entry<TapTapScreen.Authorization> { SettingsPlaceholderScreen("Authorization") }
}

fun TapTapScreen.isTabItem(): Boolean {
    return this == TapTapScreen.Game || this == TapTapScreen.Play || this == TapTapScreen.Tavern || this == TapTapScreen.Me
}
