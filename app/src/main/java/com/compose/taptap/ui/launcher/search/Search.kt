package com.compose.taptap.ui.launcher.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.taptap.ui.theme.IntlV2Black
import org.koin.androidx.compose.koinViewModel

@Composable
fun Search(
    searchViewModel: SearchViewModel = koinViewModel<SearchViewModel>()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IntlV2Black)
            .statusBarsPadding()
    ) {
        Text("Search")
    }
}
