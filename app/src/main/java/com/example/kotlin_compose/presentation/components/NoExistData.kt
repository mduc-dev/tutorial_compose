package com.example.kotlin_compose.presentation.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_compose.ui.theme.PPNeu

@Composable
fun NoExistData(textNull: String = "Emptier than the void",subTextNull: String?) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = textNull,modifier= Modifier.padding(vertical = 5.dp), style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White,
                fontFamily = PPNeu
            ))
            if (subTextNull != null) {
                Text(text = subTextNull,style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontFamily = PPNeu
                ))
            }

        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A, showSystemUi = true)
fun PreviewNoExistData(){
    NoExistData(subTextNull = "Write a post to start your profile’s never-ending journey")
}