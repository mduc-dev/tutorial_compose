package com.example.kotlin_compose.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.kotlin_compose.presentation.screens.forgotpassword.ForgotPassword
import com.example.kotlin_compose.presentation.screens.login.Login
import com.example.kotlin_compose.presentation.screens.login_without_password.LoginWithoutPassword
import com.example.kotlin_compose.presentation.screens.notifications.Notifications
import com.example.kotlin_compose.presentation.screens.search.Search
import com.example.kotlin_compose.presentation.screens.signup.Signup
import com.example.kotlin_compose.presentation.screens.welcome.Welcome

fun NavGraphBuilder.tapAuthNavigation(
    composeNavigator: AppComposeNavigator
) {
    navigation(
        route = TapTapScreens.AuthGraph.route, startDestination = TapTapScreens.Welcome.route
    ) {
        composable(route = TapTapScreens.Welcome.route) {
            Welcome(composeNavigator)
        }
        composable(route = TapTapScreens.Login.route, enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300)
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300)
            )
        }, popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300)
            )
        }, popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300)
            )
        }) {
            Login(composeNavigator)
        }
        composable(route = TapTapScreens.SignUp.route) {
            Signup(composeNavigator)
        }
        composable(route = TapTapScreens.ForgotPassword.route) {
            ForgotPassword(composeNavigator)
        }
        composable(route = TapTapScreens.LoginWithoutPassword.route) {
            LoginWithoutPassword(composeNavigator)
        }
    }
}

fun NavGraphBuilder.tapMainNavigation(composeNavigator: AppComposeNavigator) {
    navigation(
        route = TapTapScreens.MainGraph.route, startDestination = TapTapScreens.MainScaffold.route
    ) {
        composable(TapTapScreens.MainScaffold.route) {
            MainNavGraph(composeNavigator)
        }
        composable(TapTapScreens.Search.route) {
            Search()
        }
        composable(TapTapScreens.Notifications.route) {
            Notifications()
        }
    }
}
