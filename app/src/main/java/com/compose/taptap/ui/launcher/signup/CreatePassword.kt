package com.compose.taptap.ui.launcher.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.compose.taptap.R
import com.compose.taptap.core.designsystem.component.Input
import com.compose.taptap.core.designsystem.theme.IntlV2Grey60
import com.compose.taptap.core.designsystem.theme.PrimaryTextDisabledMaterialDark
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FieldMinHeight
import com.compose.taptap.core.designsystem.theme.TapTapDimens.RequirementIconSize
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme

//TODO: in this screen or in the last screen need to be defined when navigate is
//    popUpTo("screen_to_go") { inclusive = true }
//    launchSingleTop = true
// why need that? =>
// Any action that leads to a “final” screen—like Profile, SDK Web, or another destination that means the user has completed the auth flow—should clear the auth stack.
// This is to ensure the user can’t press “Back” and return to Welcome/Login/Signup after finishing the process.
// Within the flow: No popUpTo.
// Exiting/completing the flow: Yes popUpTo.

@Composable
fun CreatePassword() {
    var password by remember { mutableStateOf("") }
    val isLengthValid = password.length in 8..20
    val isLettersAndNumbers = password.any { it.isLetter() } && password.any { it.isDigit() }
    var passwordVisible by remember { mutableStateOf(false) }
    val spacing = TapTapTheme.spacing

    Input(
        value = password,
        onValueChange = { password = it },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = FieldMinHeight)
            .padding(horizontal = spacing.mediumLarge),
        shape = TapTapShape.corners.card,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        decorationBox = { innerTextField ->
            Box(
                Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart
            ) {
                if (password.isEmpty()) {
                    Text(
                        text = "Enter your password",
                        color = IntlV2Grey60,
                    )
                }
                innerTextField()

                if (!password.isEmpty()) {
                    Image(
                        painter = if (passwordVisible) painterResource(R.drawable.login_password_display_visible) else painterResource(
                            R.drawable.login_password_display_invisible
                        ),
                        contentDescription = "",
                        Modifier
                            .align(Alignment.CenterEnd)
                            .clickable(onClick = { passwordVisible = !passwordVisible })
                    )
                }
            }
        })
    //TODO:helper text
    Spacer(Modifier.height(spacing.sectionSpacing))
    Column(Modifier.padding(horizontal = spacing.mediumLarge)) {
        RequirementRow(
            iconRes = if (isLengthValid) R.drawable.login_input_text_valid_checked else R.drawable.login_input_text_valid_uncheck,
            text = "8 to 20 characters"
        )
        RequirementRow(
            iconRes = if (isLettersAndNumbers) R.drawable.login_input_text_valid_checked else R.drawable.login_input_text_valid_uncheck,
            text = "Letters and numbers"
        )
    }
}

@Composable
fun RequirementRow(
    iconRes: Int, text: String
) {
    Row(
        Modifier.padding(vertical = TapTapTheme.spacing.tiny),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TapTapTheme.spacing.small)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(RequirementIconSize)
        )
        Text(
            text,
            style = TapTapTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
                color = PrimaryTextDisabledMaterialDark
            )
        )
    }
}
