package com.compose.taptap.feature.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.compose.taptap.core.designsystem.component.templates.MainScreenTemplate
import com.compose.taptap.core.navigation.currentComposeNavigator

@Composable
fun SettingsPlaceholderScreen(
    title: String
) {
    val composeNavigator = currentComposeNavigator

    MainScreenTemplate(
        title = title,
        onBackClick = { composeNavigator.navigateUp() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Coming Soon")
        }
    }
}
