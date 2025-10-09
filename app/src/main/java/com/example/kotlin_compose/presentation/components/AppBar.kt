package com.example.kotlin_compose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin_compose.ui.theme.BlackF16
import com.example.kotlin_compose.ui.theme.PPNeu

object AppBarDefaults {
    val Height = 56.dp
}


class AppBarPreviewParameterProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf(
        "Hello", "Jetpack Compose", "Test Title"
    )
}

@Preview
@Composable
fun AppBarPreview(
    @PreviewParameter(AppBarPreviewParameterProvider::class) title: String
) {
    AppBar(title = title, contentColor = Color.White)
}

@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = BlackF16,
    contentColor: Color = Color.White,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        modifier.fillMaxWidth(), color = backgroundColor, contentColor = contentColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppBarDefaults.Height),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                Modifier
                    .width(AppBarDefaults.Height)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                navigationIcon?.invoke()
            }


            Box(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    title,
                    textAlign = TextAlign.Center,
                    fontFamily = PPNeu,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1
                )
            }
            Row(
                Modifier
                    .padding(end = 4.dp)
                    .width(AppBarDefaults.Height)
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                actions
            )
        }
    }
}