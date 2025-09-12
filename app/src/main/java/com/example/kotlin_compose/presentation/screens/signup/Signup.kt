package com.example.kotlin_compose.presentation.screens.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kotlin_compose.R
import com.example.kotlin_compose.presentation.components.AppBar
import com.example.kotlin_compose.presentation.components.Input
import com.example.kotlin_compose.presentation.navigation.AppComposeNavigator
import com.example.kotlin_compose.ui.theme.BlackF16

@Composable
fun Signup(
    composeNavigator: AppComposeNavigator
) {
    var email by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackF16)
            .statusBarsPadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        AppBar("Sign up", contentColor = colorResource(R.color.intl_v2_grey_40), navigationIcon = {
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

        Spacer(Modifier.height(30.dp))

        Input(
            value = email,
            onValueChange = { email = it },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .padding(horizontal = 16.dp),
            decorationBox = { innerTextField ->
                Box(
                    Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart
                ) {
                    if (email.isEmpty()) {
                        Text(
                            text = "Enter your email",
                            color = colorResource(R.color.intl_v2_grey_40),
                        )
                    }
                    innerTextField()
                }
            })
    }
}