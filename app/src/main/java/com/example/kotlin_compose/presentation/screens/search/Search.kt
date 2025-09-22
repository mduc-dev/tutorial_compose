package com.example.kotlin_compose.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.kotlin_compose.R

@Composable
fun Search() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.intl_v2_black))
            .statusBarsPadding()
    ) {
        Text("Search")
    }
}