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
import androidx.compose.ui.draw.shadow
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.material3.TextButton
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
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
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Spacer(Modifier.height(64.dp))
            Image(
                painter = painterResource(id = R.drawable.login_tap_home_logo),
                contentDescription = "TapTap Logo",
                modifier = Modifier
                    .width(95.dp)
                    .height(26.dp),
                contentScale = ContentScale.Fit
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
            ProtocolText(
                onTerms = {},
                onPrivacy = {},
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}

@Composable
fun ProtocolText(
    onTerms: () -> Unit,
    onPrivacy: () -> Unit,
    modifier: Modifier = Modifier
) {
    val text: AnnotatedString = buildAnnotatedString {
        append("By signing up or continuing, you agree\n")
        append("to our ")

        // "Terms" is a clickable link
        pushLink(LinkAnnotation.Clickable(tag = "terms") { onTerms() })
        pushStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.SemiBold))
        append("Terms")
        pop()   // pop style
        pop()   // pop link

        append(" and ")

        // "Privacy" is a clickable link
        pushLink(LinkAnnotation.Clickable(tag = "privacy") { onPrivacy() })
        pushStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.SemiBold))
        append("Privacy")
        pop()
        pop()
    }

    Text(
        text = text,
        color = Color.Gray,
        textAlign = TextAlign.Center,
        fontSize = 12.sp.nonScaledSp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        fontFamily = PPNeu,
        modifier = modifier
    )
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
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
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
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
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
                .height(48.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp)),
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
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
    ) {
        val w = constraints.maxWidth.toFloat()
        val h = constraints.maxHeight.toFloat()

        val rotationDeg = -22f
        val rad = Math.toRadians(kotlin.math.abs(rotationDeg).toDouble())
        val cos = kotlin.math.cos(rad)
        val sin = kotlin.math.sin(rad)
        val requiredW = (w * cos + h * sin).toFloat()
        val requiredH = (w * sin + h * cos).toFloat()
        val baseScale = maxOf(w / requiredW, h / requiredH) * 1.8f

        val density = LocalDensity.current
        val translateX = with(density) { 110.dp.toPx() }
        val translateYBase = with(density) { (-125).dp.toPx() }
        val motion = with(density) { 100.dp.toPx() }

        val infinite = rememberInfiniteTransition(label = "bg")
        // Bottom -> top first (down is +Y, so go from +motion to -motion)
        val offsetY by infinite.animateFloat(
            initialValue = motion,
            targetValue  = -motion,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 22_000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "offsetY"
        )

        Image(
            painter = painterResource(id = R.drawable.wall_paper),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    rotationZ = rotationDeg
                    translationX = translateX
                    translationY = translateYBase + offsetY
                    scaleX = baseScale
                    scaleY = baseScale * 1.15f
                }
        )

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

