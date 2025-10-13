package com.compose.taptap.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.compose.taptap.ui.launcher.welcome.WelcomeViewModel
import com.compose.taptap.ui.utils.AuthState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TapNavHost(
    navHostController: NavHostController
) {
    val viewModel: WelcomeViewModel = koinViewModel()
    val authState = viewModel.welcomeUiState.collectAsState().value
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navHostController.navigate(TapTapScreens.MainGraph.route) {
                    popUpTo(TapTapScreens.AuthGraph.route) { inclusive = true }
                }
            }

            is AuthState.Unauthenticated, is AuthState.Error -> {
                navHostController.navigate(TapTapScreens.Welcome.route) {
                    popUpTo(0) { inclusive = true }
                }
            }

            else -> Unit
        }
    }

    NavHost(navHostController, startDestination = TapTapScreens.AuthGraph.route) {
        tapAuthNavigation()
        tapMainNavigation()
    }
}