package com.example.kotlin_compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.compose.viewmodel.koinViewModel
import com.example.kotlin_compose.presentation.screens.intro.IntroViewModel
import com.example.kotlin_compose.presentation.utils.AuthState
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
fun SetupNavGraph(
    viewModel: IntroViewModel = koinViewModel<IntroViewModel>()
) {
    val authState = viewModel.introUiState.collectAsState().value
    when (authState) {
        is AuthState.Loading -> {}
        is AuthState.Authenticated -> MainNavGraph()
        is AuthState.Unauthenticated -> AuthNavGraph()
        is AuthState.Error -> AuthNavGraph()
    }
}


