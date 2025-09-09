package com.example.kotlin_compose.presentation.screens.intro

import android.graphics.BitmapFactory
import android.util.Log
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
import androidx.compose.runtime.*
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit
import com.example.kotlin_compose.ui.theme.PPNeu
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.isActive
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlinx.coroutines.delay
import kotlin.math.max

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
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.intl_v2_black))
    ) {
        // Wallpaper with mask
        WallPagerImage(resId = R.drawable.wall_paper, autoScroll = true)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xC0161616), // start
                            Color(0xFF161616)  // end
                        )
                    )
                )
        )
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
fun WallPagerImage(
    resId: Int,
    angleDeg: Float = 25f,
    autoScroll: Boolean = true
) {
    val context = LocalContext.current
    val bitmap = remember(resId) {
        BitmapFactory.decodeResource(context.resources, resId)?.asImageBitmap()
    }

    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var forward by remember { mutableStateOf(true) }
    var targetScrollY by remember { mutableFloatStateOf(0f) }

    if (bitmap != null) {
        val angleRad = Math.toRadians(angleDeg.toDouble())
        val perScrollX = tan(angleRad) * 1f
        val perScrollY = 1f

        // Animation loop (postDelayed equivalent)
        LaunchedEffect(autoScroll, bitmap, targetScrollY) {
            if (!autoScroll) return@LaunchedEffect
            while (isActive) {
                awaitFrame()

                if (forward) {
                    offsetX += perScrollX.toFloat()
                    offsetY += perScrollY
                    if (offsetY >= targetScrollY) forward = false
                } else {
                    offsetX -= perScrollX.toFloat()
                    offsetY -= perScrollY
                    if (offsetY <= 0f) forward = true
                }
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            // update targetScrollY based on actual canvas size
            val dSin = sin(angleRad) * size.width
            val dCos = cos(angleRad) * size.height
            targetScrollY = (cos(angleRad) * ((bitmap.height - dSin) - dCos))
                .toFloat()
                .coerceAtLeast(0f)

            // Scale like the original Matrix scaling
            val scale = max(
                size.width / bitmap.width,
                size.height / bitmap.height
            )

            withTransform({
                scale(scale, scale)
                rotate(-angleDeg)
                translate(offsetX, offsetY)
            }) {
                drawImage(bitmap)
            }
        }
    }
}



