package com.compose.taptap.core.designsystem.component.organisms.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.component.atoms.button.ButtonSize
import com.compose.taptap.core.designsystem.component.atoms.button.TapTapButton
import com.compose.taptap.core.designsystem.component.atoms.button.Variant
import com.compose.taptap.core.designsystem.theme.BlackF16
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.theme.WhitePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoutConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .width(320.dp)
            .background(color = Color(0xFF1F1F1F), shape = RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Log Out",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = WhitePrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Are you sure you want to log out from the current account?",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TapTapButton(
                    label = "Cancel",
                    onPress = onDismiss,
                    variant = Variant.BORDERED,
                    size = ButtonSize.LG,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    contentColor = WhitePrimary
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                TapTapButton(
                    label = "Confirm",
                    onPress = onConfirm,
                    variant = Variant.SOLID,
                    size = ButtonSize.LG,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    contentColor = BlackF16 // Use specific content color as per original design
                )
            }
        }
    }
}
