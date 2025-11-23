package com.compose.taptap.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.taptap.core.designsystem.theme.BlackF16
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchRoute(
    searchViewModel: SearchViewModel = koinViewModel()
) {
    SearchScreen()
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BlackF16)
    ) {
        Text("Search Screen - Coming Soon")
    }
}
