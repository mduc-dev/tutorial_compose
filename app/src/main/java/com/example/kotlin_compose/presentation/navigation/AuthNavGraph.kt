package com.example.kotlin_compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AuthNavGraph(composeNavigator: AppComposeNavigator) {
    val navHostController = rememberNavController()
    LaunchedEffect(Unit) {
        composeNavigator.handleNavigationCommands(navHostController)
    }
    NavHost(navHostController, startDestination = Route.INTRO) {
        tapAuthNavigation(composeNavigator)
    }
}