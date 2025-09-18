package com.example.kotlin_compose.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

//TODO: maybe need rewrite this
@Composable
fun MainNavGraph(composeNavigator: AppComposeNavigator) {
    val navHostController = rememberNavController()
    LaunchedEffect(Unit) {
        composeNavigator.handleNavigationCommands(navHostController)
    }
    val backStackState = navHostController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    selectedItem = when (backStackState?.destination?.route) {
        TapTapScreens.Home.route -> 0
        TapTapScreens.Play.route -> 1
        TapTapScreens.Tavern.route -> 2
        TapTapScreens.You.route -> 3
        else -> 0
    }

    val isBottomBarVisible = backStackState?.destination?.route in listOf(
        TapTapScreens.Home.route,
        TapTapScreens.Play.route,
        TapTapScreens.Tavern.route,
        TapTapScreens.You.route
    )

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            BottomTabNavigator(selectedItem = selectedItem, onItemClick = { index ->
                val destination = when (index) {
                    0 -> TapTapScreens.Home.route
                    1 -> TapTapScreens.Play.route
                    2 -> TapTapScreens.Tavern.route
                    3 -> TapTapScreens.You.route
                    else -> null
                }

                if (destination != null) {
                    composeNavigator.navigate(destination) {
                        navHostController.graph.startDestinationRoute?.let { startRoute ->
                            popUpTo(startRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            })
        }
    }) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = TapTapScreens.Home.route,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            tapMainNavigation(composeNavigator)
        }
    }
}

