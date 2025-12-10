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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.button.ButtonSize
import com.compose.taptap.core.designsystem.component.atoms.button.TapTapButton
import com.compose.taptap.core.designsystem.component.atoms.button.Variant
import com.compose.taptap.core.designsystem.theme.TapTapTheme

@Composable
fun TapErrorView(
    modifier: Modifier = Modifier,
    painter: Painter,
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
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(width = 150.dp, height = 135.dp)
        )
        
        Spacer(modifier = Modifier.height(TapTapTheme.spacing.medium))

        Text(
            text = title,
            style = TapTapTheme.typography.titleMedium,
            color = TapTapTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = subTitle,
            style = TapTapTheme.typography.bodyMedium,
            color = Color(0xFF999999), // Using the grey color seen in MeScreen
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        if (buttonText != null && onRetry != null) {
            Spacer(modifier = Modifier.height(24.dp))
            TapTapButton(
                label = buttonText,
                onPress = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                size = ButtonSize.LG,
                variant = Variant.SOLID
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun TapErrorViewPreview() {
    TapTapTheme {
        TapErrorView(
            painter = painterResource(id = R.drawable.sad_icon_top),
            title = "Emptier than the void",
            subTitle = "Looks like you have scribed all of your thoughts.",
            buttonText = "Retry",
            onRetry = {}
        )
    }
}
