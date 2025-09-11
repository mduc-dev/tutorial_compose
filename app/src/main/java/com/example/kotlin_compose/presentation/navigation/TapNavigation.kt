package com.example.kotlin_compose.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.kotlin_compose.presentation.screens.account.Account
import com.example.kotlin_compose.presentation.screens.home.Home
import com.example.kotlin_compose.presentation.screens.intro.Intro
import com.example.kotlin_compose.presentation.screens.login.Login
import com.example.kotlin_compose.presentation.screens.play.Play
import com.example.kotlin_compose.presentation.screens.signup.Signup
import com.example.kotlin_compose.presentation.screens.tavern.Tavern

fun NavGraphBuilder.tapAuthNavigation(
    composeNavigator: AppComposeNavigator
) {
    composable(route = Route.INTRO) {
        Intro(composeNavigator)
    }
    composable(route = Route.LOGIN) {
        Login(composeNavigator)
    }
    composable(route = Route.SIGNUP) {
        Signup(composeNavigator)
    }
}

fun NavGraphBuilder.tapMainNavigation(composeNavigator: AppComposeNavigator) {
    composable(Route.GAMES) { Home(composeNavigator) }
    composable(Route.PLAY) { Play(composeNavigator) }
    composable(Route.TAVERN) { Tavern(composeNavigator) }
    composable(Route.YOU) { Account(composeNavigator) }
}
