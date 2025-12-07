package com.compose.taptap.core.designsystem.component.organisms.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.button.ButtonSize
import com.compose.taptap.core.designsystem.component.atoms.button.TapTapButton
import com.compose.taptap.core.designsystem.component.atoms.button.Variant
import com.compose.taptap.core.designsystem.component.atoms.spacer.TapTapVerticalSpacer
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.theme.BlackF16
import com.compose.taptap.core.designsystem.theme.WhitePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCheckBottomSheet(
    onDismiss: () -> Unit, isUpdateAvailable: Boolean, onUpdate: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isDismissing by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            if (!isDismissing) {
                onDismiss()
            }
        }, sheetState = sheetState,
        containerColor = Color(0xFF1F1F1F), contentColor = WhitePrimary, dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp, top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TapTapText(
                text = "Kiểm tra bản cập nhật mới",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = WhitePrimary
            )
            TapTapVerticalSpacer(height = 8.dp)
            TapTapText(
                text = "Phiên bản hiện tại: 1.0.0",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            TapTapVerticalSpacer(height = 32.dp)

            // App Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            TapTapVerticalSpacer(height = 16.dp)

            TapTapText(
                text = "TapTap - Discover Superb Games",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = WhitePrimary
            )

            TapTapVerticalSpacer(height = 32.dp)

            // Action Button
            if (isUpdateAvailable) {
                TapTapButton(
                    label = "Cập nhật",
                    onPress = {
                        if (!isDismissing) onUpdate()
                    },
                    variant = Variant.SOLID,
                    size = ButtonSize.LG,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    contentColor = BlackF16
                )
            } else {
                TapTapButton(
                    label = "Đóng",
                    onPress = { onDismiss() },
                    variant = Variant.BORDERED,
                    size = ButtonSize.LG,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    contentColor = WhitePrimary
                )
            }
        }
    }
}
