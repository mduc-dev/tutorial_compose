package com.example.kotlin_compose.presentation.screens.welcome

import android.graphics.BitmapFactory
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin_compose.R
import com.example.kotlin_compose.presentation.navigation.AppComposeNavigator
import com.example.kotlin_compose.presentation.navigation.TapTapScreens
import com.example.kotlin_compose.presentation.utils.AuthState
import com.example.kotlin_compose.presentation.utils.Provider
import com.example.kotlin_compose.ui.theme.BlackF16
import com.example.kotlin_compose.ui.theme.PPNeu
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

val TextUnit.nonScaledSp
    @Composable get() = (this.value / LocalDensity.current.fontScale).sp


@Composable
fun Welcome(
    composeNavigator: AppComposeNavigator,
    viewModel: WelcomeViewModel = koinViewModel<WelcomeViewModel>(),
) {
    val welcomeState by viewModel.welcomeUiState.collectAsState()

    LaunchedEffect(welcomeState) {
        Log.i("UI", "Welcome saw authState = $welcomeState")
        when (welcomeState) {
            is AuthState.Authenticated -> {
                composeNavigator.navigate(TapTapScreens.MainGraph.route) {
                    popUpTo(TapTapScreens.AuthGraph.route) { inclusive = true }
                }
            }

            is AuthState.Error -> { /* show error UI */
            }

            else -> Unit
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.intl_v2_black))
    ) {
        // Wallpaper with mask
        WallPagerImage(resId = R.drawable.wall_paper)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(R.color.v3_login_home_wallpaper_mask_start_color),
                            colorResource(R.color.v3_login_home_wallpaper_mask_end_color),
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
            ThirdLoginSection(composeNavigator, viewModel)

            Spacer(modifier = Modifier.height(30.dp))

            // Login button
            TextButton(
//                enabled = !isNavigating,
                onClick = {
//                    if (!isNavigating) {
//                        isNavigating = true
                    composeNavigator.navigate(TapTapScreens.Login.route) {
                        launchSingleTop = true
                    }
//                    }
                }, modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Log in",
                    color = colorResource(id = R.color.green_primary),
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
        color = colorResource(R.color.intl_v2_auxiliary_grey_20),
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
fun ThirdLoginSection(composeNavigator: AppComposeNavigator, welcomeViewModel: WelcomeViewModel) {
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
                    color = colorResource(id = R.color.v3_login_home_third_login_button_text_color),
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
                    color = colorResource(id = R.color.v3_login_home_third_login_button_text_color),
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
                composeNavigator.navigate(TapTapScreens.SignUp.route) {
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 320.dp)
                .height(44.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_primary)),
            shape = RoundedCornerShape(24.dp),
        ) {
            Text(
                text = "Sign up",
                color = colorResource(id = R.color.v3_login_home_third_login_button_text_color),
                fontSize = 14.sp.nonScaledSp,
                fontWeight = FontWeight.Bold,
                fontFamily = PPNeu,
            )
        }
    }
}


@Composable
fun WallPagerImage(
    resId: Int, angleDeg: Float = 25f, autoScroll: Boolean = true
) {
    val resources = LocalResources.current
    val bitmap = remember(resId) {
        BitmapFactory.decodeResource(resources, resId)?.asImageBitmap()
    } ?: return

    val angleRad = Math.toRadians(angleDeg.toDouble())

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val viewW = constraints.maxWidth.toFloat()
        val viewH = constraints.maxHeight.toFloat()

        val (targetScrollY, xFactor) = remember(viewW, viewH, bitmap) {
            val dSin = sin(angleRad) * viewW
            val dCos = cos(angleRad) * viewH
            val targetY =
                (cos(angleRad) * ((bitmap.height - dSin) - dCos)).toFloat().coerceAtLeast(0f)
            val maxX = (sin(angleRad) * ((bitmap.width - dCos) - dSin)).toFloat().coerceAtLeast(0f)
            val xFac = if (targetY != 0f) maxX / targetY else 1f
            targetY to xFac
        }

        var offsetY by remember(viewW, viewH, bitmap) { mutableFloatStateOf(targetScrollY) }
        var offsetX by remember(
            viewW, viewH, bitmap
        ) { mutableFloatStateOf(xFactor * targetScrollY) }
        var forward by remember { mutableStateOf(true) }

        LaunchedEffect(autoScroll, bitmap, targetScrollY, xFactor) {
            if (!autoScroll) return@LaunchedEffect
            val scrollStep = 0.5f
            while (isActive) {
                delay(10)
                if (!forward) {
                    offsetY += scrollStep
                    offsetX += xFactor * scrollStep
                    if (offsetY >= targetScrollY) {
                        offsetY = targetScrollY
                        offsetX = xFactor * targetScrollY
                        forward = true
                    }
                } else {
                    offsetY -= scrollStep
                    offsetX -= xFactor * scrollStep
                    if (offsetY <= 0f) {
                        offsetY = 0f
                        offsetX = 0f
                        forward = false
                    }
                }
            }
        }

        Canvas(Modifier.fillMaxSize()) {
            val rotatedW = (size.width * cos(angleRad) + size.height * sin(angleRad)).toFloat()
            val rotatedH = (size.width * sin(angleRad) + size.height * cos(angleRad)).toFloat()
            val scale = max(rotatedW / bitmap.width, rotatedH / bitmap.height)

            withTransform({
                scale(scale, scale)
                rotate(-angleDeg)
                translate(-offsetX, -offsetY)
            }) {
                drawImage(bitmap)
            }
        }
    }
}
