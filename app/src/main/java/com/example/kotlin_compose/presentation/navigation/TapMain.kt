package com.example.kotlin_compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_compose.ui.theme.Kotlin_composeTheme
import org.koin.compose.koinInject

@Composable
fun TapMain() {
    val composeNavigator: AppComposeNavigator = koinInject()
    Kotlin_composeTheme {
        val navHostController = rememberNavController()

        LaunchedEffect(Unit) {
            composeNavigator.handleNavigationCommands(navHostController)
        }

        TapNavHost(navHostController, composeNavigator)
    }
}