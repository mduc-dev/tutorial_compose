package com.example.kotlin_compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject

@Composable
fun TapMain() {
    val composeNavigator: AppComposeNavigator = koinInject()
    val navHostController = rememberNavController()

    LaunchedEffect(Unit) {
        composeNavigator.handleNavigationCommands(navHostController)
    }

    TapNavHost(navHostController, composeNavigator)
}
