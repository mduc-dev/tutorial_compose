package com.example.kotlin_compose.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import org.koin.compose.viewmodel.koinViewModel
import com.example.kotlin_compose.presentation.screens.intro.Intro
import com.example.kotlin_compose.presentation.screens.intro.IntroViewModel
import com.example.kotlin_compose.presentation.screens.play.Play
import com.example.kotlin_compose.presentation.screens.tavern.Tavern
import com.example.kotlin_compose.presentation.utils.AuthState
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
fun SetupNavGraph(
    viewModel: IntroViewModel = koinViewModel<IntroViewModel>()
) {
    val navHostController = rememberNavController()
    val backStackState = navHostController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    val introUiState = viewModel.introUiState.collectAsState().value


    val startDestination = when (introUiState) {
        is AuthState.Loading -> Route.INTRO
        is AuthState.Authenticated -> Route.GAMES
        is AuthState.Unauthenticated -> Route.INTRO
        is AuthState.Error -> Route.INTRO
    }

    selectedItem = when (backStackState?.destination?.route) {
        Route.GAMES -> 0
        Route.PLAY -> 1
        Route.TAVERN -> 2
        Route.YOU -> 3
        else -> 0
    }
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.GAMES ||
                backStackState?.destination?.route == Route.PLAY ||
                backStackState?.destination?.route == Route.TAVERN ||
                backStackState?.destination?.route == Route.YOU
    }
    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            BottomTabNavigator(
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navHostController,
                            route = Route.GAMES
                        )

                        1 -> navigateToTab(
                            navController = navHostController,
                            route = Route.PLAY
                        )

                        2 -> navigateToTab(
                            navController = navHostController,
                            route = Route.TAVERN
                        )

                        3 -> navigateToTab(navController = navHostController, route = Route.YOU)
                    }
                }
            )
        }
    }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navHostController,
            startDestination = Route.GAMES,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.GAMES) {
                Intro()
            }
            composable(route = Route.PLAY) {
                Play()
            }
            composable(route = Route.TAVERN) {
                Tavern()
            }
            composable(route = Route.YOU) {
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