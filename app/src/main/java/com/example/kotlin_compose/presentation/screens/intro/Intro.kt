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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit

val TextUnit.nonScaledSp
    @Composable get() = (this.value / LocalDensity.current.fontScale).sp

@Preview
@Composable
fun Intro() {
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
            ThirdLoginSection()

            Spacer(modifier = Modifier.height(16.dp))

            // Login button
            TextButton(onClick = {}, modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    "Log in",
                    color = colorResource(id = R.color.green_primary),
                    fontSize = 16.sp.nonScaledSp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Protocol text
            Text(
                text = "By signing up or continuing, you agree \n to our Terms and Privacy", // Replace with actual protocol text
                color = Color.Gray,
                fontSize = 12.sp.nonScaledSp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)

            )
        }
    }
}

@Composable
fun ThirdLoginSection() {
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
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp.nonScaledSp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

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
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Button(
            onClick = { /* Handle sign up */ },
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
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun AnimatedBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite-transition")

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 10000, easing = LinearEasing
            ), repeatMode = RepeatMode.Reverse
        ), label = "initial-value"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image with parallax effect
        Image(
            painter = painterResource(id = R.drawable.wall_paper),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f)
                .offset(y = offset.dp * 50),
            contentScale = ContentScale.Crop
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
}