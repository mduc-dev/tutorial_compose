package com.example.kotlin_compose.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_compose.presentation.screens.account.Account
import com.example.kotlin_compose.presentation.screens.home.Home
import com.example.kotlin_compose.presentation.screens.notifications.Notifications


@Composable
fun SetupNavGraph(
) {
    val navHostController = rememberNavController()
    val backStackState = navHostController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.HOME -> 0
        Route.NOTIFICATIONS -> 1
        Route.ACCOUNT -> 2
        else -> 0
    }
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HOME ||
                backStackState?.destination?.route == Route.NOTIFICATIONS ||
                backStackState?.destination?.route == Route.ACCOUNT
    }
    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            BottomTabNavigator(
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navHostController,
                            route = Route.HOME
                        )

                        1 -> navigateToTab(
                            navController = navHostController,
                            route = Route.NOTIFICATIONS
                        )

                        2 -> navigateToTab(
                            navController = navHostController,
                            route =Route.ACCOUNT
                        )
                    }
                }
            )
        }
    }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navHostController,
            startDestination = Route.HOME,
            modifier = Modifier.padding(bottom = bottomPadding)
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
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}