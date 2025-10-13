package com.compose.taptap.ui.launcher.signup

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.compose.taptap.ui.components.AppBar
import com.compose.taptap.ui.components.DDButton
import com.compose.taptap.ui.theme.BlackF16
import com.compose.taptap.ui.theme.IntlV2Grey40
import com.compose.taptap.ui.theme.*
import com.taptap.R

fun extraSafeBottomPadding(): Dp {
    return if (Build.VERSION.SDK_INT == 29) 16.dp else 0.dp
}

@Composable
fun Signup(
) {
    val nextPressed = remember { mutableStateOf(false) }
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
            }) {
        AppBar("Sign up", contentColor = IntlV2Grey40, navigationIcon = {
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

        if (nextPressed.value) CreatePassword()
        else CreateEmail()

        Spacer(Modifier.weight(1f))

        DDButton(
            label = "Next",
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 16.dp)
                .padding(bottom = extraSafeBottomPadding()),
            onPress = {
                nextPressed.value = true
            },
//            enable = nextPressed.value
        )
    }
}
