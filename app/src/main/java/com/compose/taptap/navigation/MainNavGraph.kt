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
import androidx.navigation.toRoute
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.ui.launcher.account.Account
import com.compose.taptap.ui.launcher.game.Game
import com.compose.taptap.ui.launcher.play.Play
import com.compose.taptap.ui.launcher.tavern.Tavern

@Composable
fun MainNavGraph() {
    val innerNavController = rememberNavController()

    val currentDestination by innerNavController.currentBackStackEntryAsState()
    val currentScreen = currentDestination?.toRoute<TapTapScreen>()
    val bottomTabRoutes = BOTTOM_TAB.map { it.route }
    val isBottomBarVisible = bottomTabRoutes.contains(currentScreen)

    Scaffold(
        modifier = Modifier.fillMaxSize(), bottomBar = {
            if (isBottomBarVisible) {
                BottomTabNavigator(
                    currentRoute = currentScreen, onItemClick = { route ->
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
            startDestination = TapTapScreen.Game,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable<TapTapScreen.Game> { Game() }
            composable<TapTapScreen.Play> { Play() }
            composable<TapTapScreen.Tavern> { Tavern() }
            composable<TapTapScreen.You> { Account() }
        }
    }
}

