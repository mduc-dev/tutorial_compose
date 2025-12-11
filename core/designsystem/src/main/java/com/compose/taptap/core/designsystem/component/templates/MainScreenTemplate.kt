package com.compose.taptap.core.designsystem.component.templates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapText
import com.compose.taptap.core.designsystem.component.atoms.text.TapTapTextVariant
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.theme.WhitePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTemplate(
    title: String,
    onBackClick: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        containerColor = TapTapTheme.colors.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    TapTapText(
                        text = title,
                        variant = TapTapTextVariant.LG,
                        style = TapTapTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = WhitePrimary
                    )
                },
                navigationIcon = {
                    if (onBackClick != null) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                painter = painterResource(R.drawable.ico_24_top_bars_backward_left),
                                contentDescription = "Back",
                                tint = WhitePrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TapTapTheme.colors.background
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            content(PaddingValues())
        }
    }
}
