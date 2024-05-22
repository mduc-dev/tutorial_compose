package com.example.kotlin_compose.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlin_compose.presentation.utils.ButtonSize
import com.example.kotlin_compose.presentation.utils.ButtonVariant
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kotlin_compose.ui.theme.Black1A
import com.example.kotlin_compose.ui.theme.Green31
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun CustomButton(
    size: ButtonSize,
    variant: ButtonVariant,
    color: Color = Green31,
    enabled: Boolean? = true,
    isLoading: Boolean? = false,
    startContent: @Composable () -> Unit? = {},
    endContent: @Composable () -> Unit? = {},
    onPress: () -> Unit?,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            onClick = { onPress() },
            modifier = Modifier
                .height(ButtonDefaults.MinHeight)
                .align(Alignment.Center),
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
                disabledContainerColor = Black1A,
            ),
            enabled = enabled!!,
            border = BorderStroke(
                width = 1.dp,
                color = Black1A
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                startContent()
                if (isLoading == true) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        strokeWidth = 2.dp,
                        color = Color.White,
                        trackColor = Black1A
                    )
                }
                endContent()
            }
        }
    }

}

@Preview(apiLevel = 33, showBackground = true, backgroundColor = 0xFF1A1A1A, showSystemUi = true)
@Composable
fun PreviewButton() {
    CustomButton(
        size = ButtonSize.LG,
        variant = ButtonVariant.SOLID,
        startContent = {
            Text(text = "hello")
        },
        isLoading = true
    ) {

    }
}