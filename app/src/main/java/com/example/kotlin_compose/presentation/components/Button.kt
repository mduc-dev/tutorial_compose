package com.example.kotlin_compose.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kotlin_compose.ui.theme.Black1A
import com.example.kotlin_compose.ui.theme.Black20
import com.example.kotlin_compose.ui.theme.BlackF3
import com.example.kotlin_compose.ui.theme.Green1a
//TODO: border {color,width}, disable {color},
@Composable
fun CustomButton(
    color: Color = Green1a,
    enabled: Boolean? = true,
    isLoading: Boolean? = false,
    children: @Composable () -> Unit? = {},
    onPress: () -> Unit?,
) {
    Button(
        onClick = { onPress() },
        modifier = Modifier
            .height(ButtonDefaults.MinHeight),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            disabledContainerColor = Black20,
        ),
        shape = RoundedCornerShape(6.dp),
        enabled = enabled!!,
        border = BorderStroke(
            width = 0.5.dp,
            color = BlackF3
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isLoading == true) {
                CircularProgressIndicator(
                    progress = {
                        0.8f
                    },
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    color = Color.White,
                    strokeWidth = 2.dp,
                    trackColor = Black1A,

                    )
            }
            children()
        }
    }

}

@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
fun PreviewButton() {
    CustomButton(
//        size = ButtonSize.LG,
//        variant = ButtonVariant.SOLID,
        isLoading = true,
        children = { Text(text = "hello") },
        onPress = {},
    )
}