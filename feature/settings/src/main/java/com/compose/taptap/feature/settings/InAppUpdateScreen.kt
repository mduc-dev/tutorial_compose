package com.compose.taptap.feature.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.button.ButtonSize
import com.compose.taptap.core.designsystem.component.atoms.button.TapTapButton
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapTextVariant
import com.compose.taptap.core.designsystem.component.organisms.bottomsheet.UpdateCheckBottomSheet
import com.compose.taptap.core.designsystem.component.templates.MainScreenTemplate
import com.compose.taptap.core.designsystem.theme.BlackF16
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.theme.WhitePrimary
import com.compose.taptap.core.navigation.currentComposeNavigator
import com.compose.taptap.core.preview.TapTapPreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InAppUpdateScreen() {
    val composeNavigator = currentComposeNavigator
    var showBottomSheet by remember { mutableStateOf(false) }
    // Mock state: false means "Latest version", true means "Update available" (when checking)
    // For this UI demo, let's assume we are NOT on the latest version so we show "Check for update"
    // And inside the bottom sheet we can simulate availability.
    // Let's assume the outer button says "Check for update" if we want to trigger the flow.
    // User request: "if it not the lastest version then it will be button 'Check for update'"
    val isLatestVersion = false

    if (showBottomSheet) {
        UpdateCheckBottomSheet(
            onDismiss = { showBottomSheet = false },
            isUpdateAvailable = true, // Mocking that an update IS available for the demo
            onUpdate = { /* Handle update logic */ }
        )
    }

    MainScreenTemplate(
        title = "Check for update",
        onBackClick = { composeNavigator.navigateUp() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.update_logo),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    TapTapText(
                        text = "Version",
                        variant = TapTapTextVariant.SM,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    TapTapText(
                        text = "3.56.7-marketFull.100000",
                        style = TapTapTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = WhitePrimary
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            TapTapText(
                text = "Ding! TapTap is leveling up with patched out bugs and performance improvements.",
                style = TapTapTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.weight(1f))

            TapTapButton(
                label = if (isLatestVersion) "This is the latest version" else "Check for update",
                onPress = {
                    if (!isLatestVersion) {
                        showBottomSheet = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                size = ButtonSize.LG,
                containerColor = if (isLatestVersion) TapTapTheme.colors.surfaceContainerHighest else TapTapTheme.colors.primary,
                contentColor = if (isLatestVersion) Color.Gray else BlackF16,
                shape = RoundedCornerShape(28.dp)
            )
        }
    }
}


@Preview
@Composable
fun PreviewInAppUpdateScreen() {
    TapTapPreviewTheme {
        InAppUpdateScreen()
    }
}
