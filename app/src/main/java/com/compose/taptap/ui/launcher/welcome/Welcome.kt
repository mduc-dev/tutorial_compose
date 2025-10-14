package com.compose.taptap.ui.launcher.welcome

import android.graphics.BitmapFactory
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.taptap.R
import com.compose.taptap.core.navigation.currentComposeNavigator
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.ui.theme.*
import com.compose.taptap.ui.utils.AuthState
import com.compose.taptap.ui.utils.Provider
import kotlinx.coroutines.isActive
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

val TextUnit.nonScaledSp
    @Composable get() = (this.value / LocalDensity.current.fontScale).sp

@Composable
fun Welcome(
    viewModel: WelcomeViewModel = LocalWelcomeViewModel.current,
) {
    val composeNavigator = currentComposeNavigator
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(IntlV2Black)
    ) {
        // Wallpaper with mask
        WallPagerImage(resId = R.drawable.wall_paper)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            V3LoginHomeWallpaperMaskStartColor,
                            V3LoginHomeWallpaperMaskEndColor,
                        ), startY = 0f, endY = with(LocalDensity.current) { 450.dp.toPx() })
                )
        )
        // Content
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Spacer(Modifier.height(134.dp))
            Image(
                painter = painterResource(id = R.drawable.login_tap_home_logo),
                contentDescription = "login_tap_tap_logo",
                modifier = Modifier
                    .width(95.dp)
                    .height(26.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.weight(1f))

            // Third-party login section
            ThirdLoginSection(viewModel)

            Spacer(modifier = Modifier.height(30.dp))

            // Login button
            TextButton(
                onClick = {
                    composeNavigator.navigate(TapTapScreen.Login)
                }, modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Log in",
                    color = GreenPrimary,
                    fontSize = 14.sp.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PPNeu,
                )
            }

            ProtocolText(
                onTerms = {},
                onPrivacy = {},
            )
        }
    }
}

@Composable
fun ProtocolText(
    onTerms: () -> Unit,
    onPrivacy: () -> Unit,
) {
    val protocolText = buildAnnotatedString {
        append("By signing up or continuing, you agree\n")
        append("to our ")

        // "Terms" clickable & bold
        pushLink(LinkAnnotation.Clickable(tag = "terms") { onTerms() })
        pushStyle(SpanStyle(fontWeight = FontWeight.Medium, color = Color.White))
        append("Terms")
        pop() // style
        pop() // link

        append(" and ")

        // "Privacy" clickable & bold
        pushLink(LinkAnnotation.Clickable(tag = "privacy") { onPrivacy() })
        pushStyle(SpanStyle(fontWeight = FontWeight.Medium, color = Color.White))
        append("Privacy")
        pop() // style
        pop() // link
    }

    Text(
        text = protocolText,
        color = IntlV2AuxiliaryGrey20,
        fontSize = 12.sp.nonScaledSp,
        fontFamily = PPNeu,
        textAlign = TextAlign.Center,
        lineHeight = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 72.dp)
    )
}

@Composable
fun ThirdLoginSection(welcomeViewModel: WelcomeViewModel) {
    val composeNavigator = currentComposeNavigator
    val authState by welcomeViewModel.welcomeUiState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Error) {
            // e.g. scaffoldState.snackbarHostState.showSnackbar(...)
        }
    }

    val loadingProvider = (authState as? AuthState.Loading)?.provider

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(horizontal = 24.dp),
    ) {
        Button(
            onClick = {
                welcomeViewModel.signInWithFaceBook()
            },
            enabled = (loadingProvider != Provider.Facebook),
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 320.dp)
                .height(44.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, disabledContainerColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.icon_v2_facebook),
                    contentDescription = "Facebook",
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "Continue with Facebook",
                    color = V3LoginHomeThirdLoginButtonTextColor,
                    fontSize = 14.sp.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PPNeu,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 0.dp),
                    textAlign = TextAlign.Center
                )
                if (loadingProvider == Provider.Facebook) CircularProgressIndicator(
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    color = BlackF16,
                    strokeWidth = 1.8.dp
                )
            }
        }

        Button(
            onClick = {
                welcomeViewModel.signInWithGoogle()
            },
            enabled = (loadingProvider != Provider.Google),
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 320.dp)
                .height(44.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, disabledContainerColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.icon_v2_google),
                    contentDescription = "Google",
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "Continue with Google",
                    color = V3LoginHomeThirdLoginButtonTextColor,
                    fontSize = 14.sp.nonScaledSp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PPNeu,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 0.dp),
                    textAlign = TextAlign.Center
                )
                if (loadingProvider == Provider.Google) CircularProgressIndicator(
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    color = BlackF16,
                    strokeWidth = 1.8.dp
                )
            }
        }
        Button(
            onClick = {
                composeNavigator.navigate(TapTapScreen.SignUp)
            },
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 320.dp)
                .height(44.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
            shape = RoundedCornerShape(24.dp),
        ) {
            Text(
                text = "Sign up",
                color = V3LoginHomeThirdLoginButtonTextColor,
                fontSize = 14.sp.nonScaledSp,
                fontWeight = FontWeight.Bold,
                fontFamily = PPNeu,
            )
        }
    }
}

private const val DEFAULT_ANIMATION_DURATION_MS = 30_000

@Composable
fun WallPagerImage(
    resId: Int,
    angleDeg: Float = 25f,
    autoScroll: Boolean = true,
    animationDurationMillis: Int = DEFAULT_ANIMATION_DURATION_MS
) {
    val resources = LocalResources.current

    val bitmap = remember(resId) {
        BitmapFactory.decodeResource(resources, resId)?.asImageBitmap()
    } ?: return

    val angleRad = remember(angleDeg) { Math.toRadians(angleDeg.toDouble()) }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val viewWidth = constraints.maxWidth.toFloat()
        val viewHeight = constraints.maxHeight.toFloat()

        val (maxScrollY, _, maxScrollX) = remember(
            viewWidth, viewHeight, bitmap, angleRad
        ) {
            val sinAngle = sin(angleRad)
            val cosAngle = cos(angleRad)

            val projectedBitmapHeightReduction = sinAngle * viewWidth
            val projectedBitmapWidthReduction = cosAngle * viewHeight

            val targetY =
                (cosAngle * (bitmap.height - projectedBitmapHeightReduction) - sinAngle * viewHeight).toFloat()
                    .coerceAtLeast(0f)

            val targetX =
                (sinAngle * (bitmap.width - projectedBitmapWidthReduction) - cosAngle * viewWidth).toFloat()
                    .coerceAtLeast(0f)

            val factorX = if (targetY != 0f) targetX / targetY else 1f

            Triple(targetY, factorX, factorX * targetY)
        }

        val progress = remember { Animatable(0f) }

        LaunchedEffect(autoScroll, maxScrollY, maxScrollX, animationDurationMillis) {
            progress.snapTo(0f)
            if (!autoScroll || maxScrollY == 0f) return@LaunchedEffect

            while (isActive) {
                progress.animateTo(
                    targetValue = 1f, animationSpec = tween(
                        durationMillis = animationDurationMillis, easing = LinearEasing
                    )
                )
                progress.animateTo(
                    targetValue = 0f, animationSpec = tween(
                        durationMillis = animationDurationMillis, easing = LinearEasing
                    )
                )
            }
        }

        val offsetX by remember { derivedStateOf { progress.value * maxScrollX } }
        val offsetY by remember { derivedStateOf { progress.value * maxScrollY } }

        Canvas(Modifier.fillMaxSize()) {
            val currentViewWidth = size.width
            val currentViewHeight = size.height

            val cosAngle = cos(angleRad).toFloat()
            val sinAngle = sin(angleRad).toFloat()

            val rotatedContentWidth = currentViewWidth * cosAngle + currentViewHeight * sinAngle
            val rotatedContentHeight = currentViewWidth * sinAngle + currentViewHeight * cosAngle

            val scale =
                max(rotatedContentWidth / bitmap.width, rotatedContentHeight / bitmap.height)

            withTransform({
                rotate(degrees = -angleDeg)

                scale(scaleX = scale, scaleY = scale)

                translate(left = -offsetX, top = -offsetY)
            }) {
                drawImage(image = bitmap)
            }
        }
    }
}
