package com.compose.taptap.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.ui.launcher.welcome.LocalWelcomeViewModel
import com.compose.taptap.ui.launcher.welcome.WelcomeViewModel
import com.compose.taptap.ui.theme.BlackF16
import com.compose.taptap.ui.utils.AuthState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TapNavHost(
    navHostController: NavHostController
) {
    val welcomeViewModel: WelcomeViewModel = koinViewModel()
    val authState = welcomeViewModel.welcomeUiState.collectAsStateWithLifecycle().value

    CompositionLocalProvider(LocalWelcomeViewModel provides welcomeViewModel) {
        when (authState) {
            is AuthState.Authenticated ->
                TapMainNavigationHost(navHostController)

            is AuthState.Unauthenticated,
            is AuthState.Idle,
            is AuthState.Error,
            is AuthState.Loading -> {
                TapAuthNavigationHost(navHostController)
            }
        }
    }
}

@Composable
private fun TapAuthNavigationHost(navHostController: NavHostController) {
    NavHost(
        navHostController,
        startDestination = TapTapScreen.AuthGraph,
        modifier = Modifier
            .background(BlackF16)
            .fillMaxSize()
    ) {
        tapAuthNavigation()
    }
}

@Composable
private fun TapMainNavigationHost(navHostController: NavHostController) {
    val currentRoute =
        navHostController.currentBackStackEntryAsState().value?.destination?.route?.substringBefore(
            '?'
        )
    val currentScreen = when (currentRoute) {
        TapTapScreen.Game::class.qualifiedName -> TapTapScreen.Game
        TapTapScreen.Play::class.qualifiedName -> TapTapScreen.Play
        TapTapScreen.Tavern::class.qualifiedName -> TapTapScreen.Tavern
        TapTapScreen.You::class.qualifiedName -> TapTapScreen.You
        else -> null
    }

    val bottomDestinations = remember {
        setOf(TapTapScreen.Game, TapTapScreen.Play, TapTapScreen.Tavern, TapTapScreen.You)
    }

    Scaffold(
        bottomBar = {
            if (currentScreen in bottomDestinations) {
                BottomTabNavigator(currentRoute = currentScreen) { target ->
                    navHostController.navigate(target) {
                        popUpTo(TapTapScreen.Game) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navHostController,
            startDestination = TapTapScreen.MainGraph,
            modifier = Modifier
                .padding(innerPadding)
                .background(BlackF16)
                .fillMaxSize()
        ) {
            tapMainNavigation()
        }
    }
}

