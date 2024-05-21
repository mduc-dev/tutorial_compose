package com.example.kotlin_compose.presentation.screens.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kotlin_compose.presentation.components.NoExistData
import com.example.kotlin_compose.presentation.utils.isEmpty

@Composable
fun Account() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(isEmpty(null))
                NoExistData(subTextNull = "Write a post to start your profile’s never-ending journey.")
            else {
                Text(text = "hello")
            }

        }
    }
}


@Preview(apiLevel = 33, showBackground = true, backgroundColor = 0xFF1A1A1A, showSystemUi = true)
@Composable
fun AccountPreview() {
    Account()
}