package com.compose.taptap.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.feature.account.Account
import com.compose.taptap.feature.auth.forgotpassword.ForgotPasswordScreen
import com.compose.taptap.feature.auth.login.LoginScreen
import com.compose.taptap.feature.auth.loginwithoutpassword.LoginWithoutPasswordScreen
import com.compose.taptap.feature.auth.signup.SignupScreen
import com.compose.taptap.feature.auth.welcome.WelcomeScreen
import com.compose.taptap.feature.game.GameRoute
import com.compose.taptap.feature.game_detail.GameDetail
import com.compose.taptap.feature.notifications.Notifications
import com.compose.taptap.feature.play.Play
import com.compose.taptap.feature.search.SearchRoute
import com.compose.taptap.feature.settings.InAppUpdateScreen
import com.compose.taptap.feature.settings.SettingsPlaceholderScreen
import com.compose.taptap.feature.settings.SettingsScreen
import com.compose.taptap.feature.tavern.Tavern

fun NavGraphBuilder.tapAuthNavigation(
) {
    navigation<TapTapScreen.AuthGraph>(
        startDestination = TapTapScreen.Welcome
    ) {
        composable<TapTapScreen.Welcome> {
            WelcomeScreen()
        }
        composable<TapTapScreen.Login>(enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(350)
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(350)
            )
        }, popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(350)
            )
        }, popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(350)
            )
        }) {
            LoginScreen()
        }
        composable<TapTapScreen.SignUp>(enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(350)
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(350)
            )
        }, popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(350)
            )
        }, popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(350)
            )
        }) {
            SignupScreen()
        }
        composable<TapTapScreen.ForgotPassword> {
            ForgotPasswordScreen()
        }
        composable<TapTapScreen.LoginWithoutPassword> {
            LoginWithoutPasswordScreen()
        }
    }
}

fun NavGraphBuilder.tapMainNavigation(
    onToggleFlip: (Boolean, String?) -> Unit
) {
    navigation<TapTapScreen.MainGraph>(
        startDestination = TapTapScreen.Game
    ) {
        composable<TapTapScreen.Game> {
            GameRoute()
        }
        composable<TapTapScreen.Play> { Play(onToggleFlip = onToggleFlip) }
        composable<TapTapScreen.Tavern> { Tavern() }
        composable<TapTapScreen.You> { Account() }
        composable<TapTapScreen.Search> {
            SearchRoute()
        }
        composable<TapTapScreen.Notifications> {
            Notifications()
        }
        composable<TapTapScreen.GameDetail> {
            GameDetail()
        }
        composable<TapTapScreen.Settings> {
            SettingsScreen(
                onLogout = {} // Placeholder for logout logic
            )
        }
        composable<TapTapScreen.InAppUpdate> {
            InAppUpdateScreen()
        }
        composable<TapTapScreen.GameUpdate> {
            SettingsPlaceholderScreen("Game Update")
        }

        // Settings Placeholders
        composable<TapTapScreen.Feedback> { SettingsPlaceholderScreen("Feedback") }
        composable<TapTapScreen.AccountSecurity> { SettingsPlaceholderScreen("Account and Security") }
        composable<TapTapScreen.DarkMode> { SettingsPlaceholderScreen("Dark mode") }
        composable<TapTapScreen.Languages> { SettingsPlaceholderScreen("Languages") }
        composable<TapTapScreen.OrdersPayments> { SettingsPlaceholderScreen("Orders & Payments") }
        composable<TapTapScreen.General> { SettingsPlaceholderScreen("General") }
        composable<TapTapScreen.Redeem> { SettingsPlaceholderScreen("Redeem") }
        composable<TapTapScreen.DownloadInstall> { SettingsPlaceholderScreen("Download & Install") }
        composable<TapTapScreen.NotificationSettings> { SettingsPlaceholderScreen("Notification settings") }
        composable<TapTapScreen.AboutTapTap> { SettingsPlaceholderScreen("About TapTap") }
        composable<TapTapScreen.TermsOfService> { SettingsPlaceholderScreen("Terms of Service") }
        composable<TapTapScreen.Privacy> { SettingsPlaceholderScreen("Privacy") }
        composable<TapTapScreen.PrivacyPolicy> { SettingsPlaceholderScreen("Privacy Policy") }
        composable<TapTapScreen.Authorization> { SettingsPlaceholderScreen("Authorization") }
    }
}

fun TapTapScreen.isTabItem(): Boolean {
    return this == TapTapScreen.Game || this == TapTapScreen.Play || this == TapTapScreen.Tavern || this == TapTapScreen.You
}
