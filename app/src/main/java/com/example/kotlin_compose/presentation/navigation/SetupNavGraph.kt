package com.example.kotlin_compose.presentation.navigation

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.composable
import com.example.kotlin_compose.presentation.screens.account.Account
import com.example.kotlin_compose.presentation.screens.home.Home
import com.example.kotlin_compose.presentation.screens.notifications.Notifications

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    onExitApp: (() -> Unit?)? = null
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.HOME
    ) {
        composable(route = Route.HOME) {
            Home()
        }
        composable(route = Route.NOTIFICATIONS) {
            Notifications()
        }
        composable(route = Route.ACCOUNT) {
            Account()
        }
    }
}