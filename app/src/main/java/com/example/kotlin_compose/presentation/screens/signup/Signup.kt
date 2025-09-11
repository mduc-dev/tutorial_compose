package com.example.kotlin_compose.presentation.screens.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.kotlin_compose.R
import com.example.kotlin_compose.presentation.components.AppBar
import com.example.kotlin_compose.presentation.navigation.AppComposeNavigator
import com.example.kotlin_compose.ui.theme.BlackF16
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Signup(
    composeNavigator: AppComposeNavigator
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackF16)
    ) {
        AppBar("Sign up", navigationIcon = {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var email by remember { mutableStateOf("") }

            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter your email") },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}