package com.compose.taptap.core.designsystem.component.molecules.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.button.ButtonSize
import com.compose.taptap.core.designsystem.component.atoms.button.TapTapButton
import com.compose.taptap.core.designsystem.component.atoms.button.Variant
import com.compose.taptap.core.designsystem.theme.TapTapDimens
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapErrorView(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    subTitle: String,
    buttonText: String? = null,
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(width = TapTapDimens.ErrorImageWidth, height = TapTapDimens.ErrorImageHeight)
        )
        
        Spacer(modifier = Modifier.height(TapTapTheme.spacing.medium))

        Text(
            text = title,
            style = TapTapTheme.typography.titleMedium,
            color = TapTapTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(TapTapTheme.spacing.small))

        Text(
            text = subTitle,
            style = TapTapTheme.typography.bodyMedium,
            color = TapTapTheme.colors.textGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = TapTapTheme.spacing.xLarge)
        )

        if (buttonText != null && onRetry != null) {
            Spacer(modifier = Modifier.height(TapTapTheme.spacing.large))
            TapTapButton(
                onPress = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = TapTapTheme.spacing.xLarge),
                size = ButtonSize.LG,
                variant = Variant.SOLID,
                contentColor = TapTapTheme.colors.scrim
            ) {
                Text(
                    text = buttonText,
                    style = TapTapTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = TapTapTheme.colors.scrim
                )
            }
        }
    }
}

@Preview
@Composable
private fun TapErrorViewPreview() {
    TapTapTheme(darkTheme = true, dynamicColor = false) {
        TapErrorView(
            icon = R.drawable.sad_icon_top,
            title = "Emptier than the void",
            subTitle = "Looks like you have scribed all of your thoughts.",
            buttonText = "Retry",
            onRetry = {}
        )
    }
}
