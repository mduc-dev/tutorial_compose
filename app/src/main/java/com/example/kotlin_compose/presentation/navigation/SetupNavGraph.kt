package com.example.kotlin_compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.compose.viewmodel.koinViewModel
import com.example.kotlin_compose.presentation.screens.intro.IntroViewModel
import com.example.kotlin_compose.presentation.utils.AuthState
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
fun SetupNavGraph() {
    val viewModel: IntroViewModel = koinViewModel()
    val authState = viewModel.introUiState.collectAsState().value
    val navigator: AppComposeNavigator = koinInject()

    when (authState) {
        is AuthState.Loading -> {}
        is AuthState.Authenticated -> MainNavGraph(composeNavigator = navigator)
        is AuthState.Unauthenticated -> AuthNavGraph(composeNavigator = navigator)
        is AuthState.Error -> AuthNavGraph(composeNavigator = navigator)
    }
}


