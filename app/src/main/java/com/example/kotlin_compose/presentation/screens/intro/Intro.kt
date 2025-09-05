package com.example.kotlin_compose.presentation.screens.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin_compose.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import com.example.kotlin_compose.ui.theme.PPNeu

val TextUnit.nonScaledSp
    @Composable get() = (this.value / LocalDensity.current.fontScale).sp

@Preview
@Composable
fun Intro(
//    viewModel: IntroViewModel  = koinViewModel<IntroViewModel>(),
    onNavigateToLogin: () -> Unit = {},
    onNavigateToSignup: () -> Unit = {}
) {
//    val introState = viewModel.introUiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Wallpaper with mask
        AnimatedBackground()

        // Content
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Spacer(modifier = Modifier.height(120.dp))
            Image(
                painter = painterResource(id = R.drawable.login_tap_home_logo),
                contentDescription = "TapTap Logo",
                modifier = Modifier
                    .width(95.dp)
                    .height(26.dp),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.weight(1f))

            // Third-party login section
            ThirdLoginSection(onNavigateToSignup)

            Spacer(modifier = Modifier.height(16.dp))

            // Login button
            TextButton(onClick = onNavigateToLogin, modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    "Log in",
                    color = colorResource(id = R.color.green_primary),
                    fontSize = 16.sp.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    fontFamily = PPNeu,
                )
            }

            // Protocol text
            Text(
                buildAnnotatedString {
                    append("By signing up or continuing, you agree \n to our ")

                    withStyle(SpanStyle(color = Color.White)) {
                        append("Terms ")
                    }

                    append("and ")

                    withStyle(SpanStyle(color = Color.White)) {
                        append("Privacy")
                    }
                },
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 12.sp.nonScaledSp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                fontFamily = PPNeu,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}

@Composable
fun ThirdLoginSection(onNavigateToSignup: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(horizontal = 24.dp),
    ) {
        Button(
            onClick = { /* Handle Facebook login */ },
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 320.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.icon_v2_facebook),
                    contentDescription = "Facebook",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                )

                Text(
                    text = "Continue with Facebook",
                    color = Color.Black,
                    fontSize = 16.sp.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    fontFamily = PPNeu,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 320.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.icon_v2_google),
                    contentDescription = "Facebook",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                )

                Text(
                    text = "Continue with Google",
                    color = Color.Black,
                    fontSize = 16.sp.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    fontFamily = PPNeu,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Button(
            onClick = onNavigateToSignup,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 320.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_primary)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Sign up",
                color = Color.Black,
                fontSize = 16.sp.nonScaledSp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                fontFamily = PPNeu,
            )
        }
    }
}

@Composable
private fun AnimatedBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite-transition")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -500f, // adjust scroll distance
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scrollAnim"
    )

    // Background image with parallax effect
    Image(
        painter = painterResource(id = R.drawable.wall_paper),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .graphicsLayer {
                rotationZ = -15f // tilt ~11 o'clock
                translationY = offsetY
            }
            .fillMaxSize()
    )

    // Gradient overlay
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.6f),
                        Color.Black.copy(alpha = 0.8f),
                        Color.Black.copy(alpha = 1f),
                        Color.Black.copy(alpha = 2f)
                    )
                )
            )
    )
}