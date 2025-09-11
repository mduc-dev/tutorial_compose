package com.example.kotlin_compose.presentation.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.kotlin_compose.R
import com.example.kotlin_compose.presentation.components.AppBar
import com.example.kotlin_compose.presentation.navigation.AppComposeNavigator
import com.example.kotlin_compose.ui.theme.BlackF16

@Composable
fun Login(composeNavigator: AppComposeNavigator) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlackF16)
    ) {
        AppBar("Log in", navigationIcon = {
            IconButton(
                onClick = {
                    composeNavigator.navigateUp()
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back_v2),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        })
    }
}