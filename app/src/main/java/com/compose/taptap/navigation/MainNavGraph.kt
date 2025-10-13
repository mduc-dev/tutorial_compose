package com.compose.taptap.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.compose.presentation.screens.account.Account
import com.compose.presentation.screens.game.Game
import com.compose.presentation.screens.play.Play
import com.compose.presentation.screens.tavern.Tavern

@Composable
fun MainNavGraph(composeNavigator: AppComposeNavigator) {
    val innerNavController = rememberNavController()

    val backStackState by innerNavController.currentBackStackEntryAsState()
    val currentRoute = backStackState?.destination?.route

    val isBottomBarVisible = currentRoute contains BOTTOM_TAB.map { it.route }

    Scaffold(
        modifier = Modifier.fillMaxSize(), bottomBar = {
            if (isBottomBarVisible) {
                BottomTabNavigator(
                    currentRoute = currentRoute, onItemClick = { route ->
                        innerNavController.navigate(route) {
                            innerNavController.graph.startDestinationRoute?.let { startRoute ->
                                popUpTo(startRoute) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }
        }) { innerPadding ->
        NavHost(
            navController = innerNavController,
            startDestination = TapTapScreens.Game.route,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable(TapTapScreens.Game.route) { Game(composeNavigator) }
            composable(TapTapScreens.Play.route) { Play(composeNavigator) }
            composable(TapTapScreens.Tavern.route) { Tavern(composeNavigator) }
            composable(TapTapScreens.You.route) { Account(composeNavigator) }
        }
    }
}

