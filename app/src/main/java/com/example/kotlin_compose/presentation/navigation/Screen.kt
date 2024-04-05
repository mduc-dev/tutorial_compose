package com.example.kotlin_compose.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen(Routes.HOME)
    data object Account : Screen(Routes.ACCOUNT)
    data object Notifications : Screen(Routes.NOTIFICATIONS)
}