package com.example.kotlin_compose.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.kotlin_compose.presentation.screens.play.Play
import com.example.kotlin_compose.presentation.screens.tavern.Tavern

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    selectedItem = when (backStackState?.destination?.route) {
        Route.GAMES -> 0
        Route.PLAY -> 1
        Route.TAVERN -> 2
        Route.YOU -> 3
        else -> 0
    }

    val isBottomBarVisible = backStackState?.destination?.route in listOf(
        Route.GAMES, Route.PLAY, Route.TAVERN, Route.YOU
    )

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            BottomTabNavigator(selectedItem = selectedItem, onItemClick = { index ->
                when (index) {
                    0 -> navigateToTab(navController, Route.GAMES)
                    1 -> navigateToTab(navController, Route.PLAY)
                    2 -> navigateToTab(navController, Route.TAVERN)
                    3 -> navigateToTab(navController, Route.YOU)
                }
            })
        }
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.GAMES,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable(Route.GAMES) { Home() }
            composable(Route.PLAY) { Play() }
            composable(Route.TAVERN) { Tavern() }
            composable(Route.YOU) { Account() }
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
