package com.compose.taptap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.R
import com.compose.taptap.ui.theme.PPNeu

@Composable
fun NoExistData(
    textNull: String = "Emptier than the void",
    subTextNull: String?,
    painterResourceName: Int? = R.drawable.sad_icon_right,
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = painterResourceName!!),
                contentDescription = null,
            )
            Text(
                text = textNull,
                modifier = modifier.padding(vertical = 5.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontFamily = PPNeu,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
            if (subTextNull != null) {
                Text(
                    text = subTextNull, style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White, fontFamily = PPNeu, textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
@Preview(apiLevel = 36, showBackground = true, backgroundColor = 0xFF1A1A1A, showSystemUi = true)
fun PreviewNoExistData() {
    NoExistData(
        subTextNull = "Write a post to start your profile’s never-ending journey",
        painterResourceName = R.drawable.confuse_icon,
        modifier = Modifier
    )
}