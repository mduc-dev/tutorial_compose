package com.example.kotlin_compose.presentation.screens.login_without_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin_compose.R
import com.example.kotlin_compose.presentation.components.DDButton
import com.example.kotlin_compose.presentation.components.Input
import com.example.kotlin_compose.presentation.navigation.AppComposeNavigator
import com.example.kotlin_compose.presentation.screens.signup.extraSafeBottomPadding
import com.example.kotlin_compose.presentation.screens.welcome.nonScaledSp
import com.example.kotlin_compose.ui.theme.BlackF16
import com.example.kotlin_compose.ui.theme.PPNeu


@Composable
fun LoginWithoutPassword(composeNavigator: AppComposeNavigator) {
    Column(
        Modifier
            .fillMaxSize()
            .background(BlackF16)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        var email by remember { mutableStateOf("") }
        Text(
            "Reset password", fontFamily = PPNeu,
            fontSize = 22.sp.nonScaledSp,
            fontWeight = FontWeight.Bold,
        )
        Input(
            value = email,
            onValueChange = { email = it },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Unspecified
            ),
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
                    if (email.isNotEmpty()) {
                        Image(
                            painterResource(R.drawable.login_input_number_clear),
                            contentDescription = "Clear email",
                            Modifier
                                .align(Alignment.CenterEnd)
                                .clickable(onClick = { email = "" })
                        )
                    }
                }
            })

        Text(
            "We’ll email you a code to log in.",
            color = colorResource(R.color.primary_text_disabled_material_dark),
            fontFamily = PPNeu,
            fontSize = 14.sp.nonScaledSp,
            fontWeight = FontWeight.Normal,
        )

        Spacer(Modifier.weight(1f))

        DDButton(
            label = "Send code",
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding()
                .padding(bottom = extraSafeBottomPadding()),
            onPress = {},
            enable = false
        )
    }
}