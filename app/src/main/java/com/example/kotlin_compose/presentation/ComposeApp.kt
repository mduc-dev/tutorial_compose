package com.example.kotlin_compose.presentation

import androidx.compose.runtime.Composable
import com.example.kotlin_compose.presentation.navigation.SetupNavGraph
import androidx.navigation.NavHostController

@Composable
fun ComposeApp(navHostController: NavHostController) {
    SetupNavGraph(
        navHostController = navHostController
    )
}


