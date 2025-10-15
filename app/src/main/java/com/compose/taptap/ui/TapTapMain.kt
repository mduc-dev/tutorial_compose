package com.compose.taptap.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.compose.taptap.core.navigation.AppComposeNavigator
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.navigation.TapNavHost
import com.compose.taptap.ui.theme.Kotlin_composeTheme

@Composable
fun TapTapMain(composeNavigator: AppComposeNavigator<TapTapScreen>) {
    Kotlin_composeTheme(darkTheme = true) {
        val navHostController = rememberNavController()
        LaunchedEffect(Unit) {
            composeNavigator.handleNavigationCommands(navHostController)
        }
        TapNavHost(navHostController)
    }
}
