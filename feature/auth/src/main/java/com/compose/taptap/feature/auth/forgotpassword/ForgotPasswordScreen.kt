package com.compose.taptap.feature.auth.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
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
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.button.TapTapButton
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapTextVariant
import com.compose.taptap.core.designsystem.component.atoms.textfield.TapTapTextField
import com.compose.taptap.core.designsystem.theme.IntlV2Grey40
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FieldMinHeight
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.feature.auth.signup.extraSafeBottomPadding


@Composable
fun ForgotPasswordScreen() {
    val spacing = TapTapTheme.spacing
    Column(
        Modifier
            .fillMaxSize()
            .background(TapTapTheme.colors.background)
            .statusBarsPadding()
            .padding(horizontal = spacing.gutter),
        verticalArrangement = Arrangement.spacedBy(spacing.gutter)
    ) {
        var email by remember { mutableStateOf("") }
        TapTapText(
            text = "Reset password",
            variant = TapTapTextVariant.LG,
            style = TapTapTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = TapTapTheme.colors.onBackground,
        )
        TapTapTextField(
            value = email,
            onValueChange = { email = it },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = FieldMinHeight),
            shape = TapTapShape.corners.card,
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
                        TapTapText(
                            text = "Enter your email",
                            color = IntlV2Grey40,
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

        TapTapText(
            text = "We’ll email you a code to reset your password.",
            variant = TapTapTextVariant.SM,
            style = TapTapTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = TapTapTheme.colors.onSurfaceVariant,
        )

        Spacer(Modifier.weight(1f))

        TapTapButton(
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
