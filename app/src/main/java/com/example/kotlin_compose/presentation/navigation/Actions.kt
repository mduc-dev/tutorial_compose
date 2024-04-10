package com.example.kotlin_compose.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import com.example.kotlin_compose.data.local.BottomNavigation

object Route {
    const val HOME = "home"
    const val NOTIFICATIONS = "notifications"
    const val ACCOUNT = "account"
}


val BOTTOM_TAB =
    listOf(
        BottomNavigation(
            title = "Home",
            icon = Icons.Rounded.Home,
            route = Route.HOME
        ),
        BottomNavigation(
            title = "Notifications",
            icon = Icons.Rounded.Notifications,
            route = Route.NOTIFICATIONS
        ),
        BottomNavigation(
            title = "Account",
            icon = Icons.Rounded.AccountCircle,
            route = Route.ACCOUNT
        ),
    )