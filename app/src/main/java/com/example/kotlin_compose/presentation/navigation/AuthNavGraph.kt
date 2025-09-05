package com.example.kotlin_compose.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_compose.presentation.screens.intro.Intro
import com.example.kotlin_compose.presentation.screens.login.Login
import com.example.kotlin_compose.presentation.screens.signup.Signup

@Composable
fun AuthNavGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Route.INTRO) {
        composable(route = Route.INTRO) {
            Intro(onNavigateToLogin = {
                navController.navigate(Route.LOGIN)
            }, onNavigateToSignup = {
                navController.navigate(Route.SIGNUP)
            })
        }
        composable(route = Route.LOGIN) {
            Login()
        }
        composable(route = Route.SIGNUP) {
            Signup()
        }
    }
}