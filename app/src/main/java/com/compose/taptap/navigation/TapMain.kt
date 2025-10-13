package com.compose.taptap.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.compose.taptap.core.navigation.AppComposeNavigator
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.ui.theme.Kotlin_composeTheme
import org.koin.compose.koinInject

@Composable
fun TapMain(composeNavigator: AppComposeNavigator<TapTapScreen> = koinInject()) {
    Kotlin_composeTheme(darkTheme = true) {
        val navHostController = rememberNavController()
        LaunchedEffect(Unit) {
            composeNavigator.handleNavigationCommands(navHostController)
        }

        TapNavHost(navHostController)
    }
}
