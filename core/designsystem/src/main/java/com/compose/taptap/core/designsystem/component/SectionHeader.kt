package com.compose.taptap.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.taptap.core.designsystem.theme.IntlV2Grey60
import com.compose.taptap.core.designsystem.theme.PPNeu

@Composable
fun SectionHeader(
    title: String,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = White,
                fontFamily = PPNeu,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Row(
            modifier = Modifier
                .clickable { onMoreClick() }
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "More", color = IntlV2Grey60, fontFamily = PPNeu, fontSize = 12.sp
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "More",
                tint = IntlV2Grey60,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
