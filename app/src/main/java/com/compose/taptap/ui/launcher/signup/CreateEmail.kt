package com.compose.taptap.ui.launcher.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.compose.R
import com.compose.taptap.ui.components.Input
import com.compose.taptap.ui.theme.IntlV2Grey60
import com.compose.ui.theme.*

@Composable
fun CreateEmail() {
    var email by remember { mutableStateOf("") }
    Input(
        value = email,
        onValueChange = { email = it },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .padding(horizontal = 16.dp),
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
                        color = IntlV2Grey60,
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
    //TODO:helper text
}
