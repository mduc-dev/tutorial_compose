package com.compose.taptap.feature.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.compose.taptap.core.designsystem.R
import com.compose.taptap.core.designsystem.component.molecules.appbar.TapTapAppBar
import com.compose.taptap.core.designsystem.component.atoms.button.DDButton
import com.compose.taptap.core.designsystem.component.atoms.textfield.TapTapTextField
import com.compose.taptap.core.designsystem.component.molecules.state.EmptyStateView
import com.compose.taptap.core.designsystem.theme.BlackDisable
import com.compose.taptap.core.designsystem.theme.IntlCcGreenPrimary
import com.compose.taptap.core.designsystem.theme.IntlV2Grey60
import com.compose.taptap.core.designsystem.theme.PrimaryTextDisabledMaterialDark
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FieldMinHeight
import com.compose.taptap.core.designsystem.theme.TapTapDimens.FieldVerticalSpacing
import com.compose.taptap.core.designsystem.theme.TapTapDimens.TabBottomPadding
import com.compose.taptap.core.designsystem.theme.TapTapShape
import com.compose.taptap.core.designsystem.theme.TapTapTheme
import com.compose.taptap.core.designsystem.util.DisabledInteractionSource
import com.compose.taptap.core.navigation.AppComposeNavigator
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.core.navigation.currentComposeNavigator
import com.compose.taptap.feature.auth.signup.extraSafeBottomPadding
import kotlinx.coroutines.launch

val tabs = listOf("Email", "Phone")

@Composable
fun LoginScreen() {
    val composeNavigator = currentComposeNavigator
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val density = LocalDensity.current
    val spacing = TapTapTheme.spacing
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .fillMaxSize()
            .background(TapTapTheme.colors.background)
            .statusBarsPadding()
    ) {
        TapTapAppBar("Log in", navigationIcon = {
            IconButton(
                onClick = {
                    composeNavigator.navigateUp()
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back_v2),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        })

        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = TapTapTheme.colors.background,
            divider = {},
            modifier = Modifier.padding(vertical = spacing.mediumLarge),
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(pagerState.currentPage),
                    width = tabWidths[pagerState.currentPage],
                    color = IntlCcGreenPrimary
                )
            }) {
            tabs.forEachIndexed { tabIndex, item ->
                Tab(
                    selectedContentColor = Color.White,
                    unselectedContentColor = BlackDisable,
                    selected = tabIndex == pagerState.currentPage,
                    interactionSource = DisabledInteractionSource(),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(tabIndex)
                        }
                    }) {
                    Text(
                        text = item,
                        style = TapTapTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = TabBottomPadding),
                        onTextLayout = { textLayoutResult ->
                            tabWidths[tabIndex] =
                                with(density) { textLayoutResult.size.width.toDp() }
                        })
                }
            }
        }

        PageContent(
            pagerState, modifier = Modifier
                .weight(1f)
                .pointerInput(UInt) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }, composeNavigator
        )
    }
}

@Composable
fun PageContent(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    composeNavigator: AppComposeNavigator<TapTapScreen>
) {
    val spacing = TapTapTheme.spacing
    HorizontalPager(
        state = pagerState, modifier
    ) { page ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.mediumLarge),
            contentAlignment = Alignment.TopCenter
        ) {
            when (page) {
                0 -> Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(FieldVerticalSpacing)
                ) {
                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var passwordVisible by remember { mutableStateOf(false) }

                    TapTapTextField(
                        value = email,
                        onValueChange = { email = it },
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = FieldMinHeight),
                        shape = TapTapShape.corners.card,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Unspecified,
                            autoCorrectEnabled = false,
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Unspecified
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart
                            ) {
                                if (email.isEmpty()) {
                                    Text(
                                        text = "Enter your email",
                                        color = IntlV2Grey60,
                                    )
                                }
                                innerTextField()
                                if (email.isNotEmpty()) {
                                    Image(
                                        painterResource(R.drawable.login_input_number_clear),
                                        contentDescription = "Clear email",
                                        Modifier
                                            .align(Alignment.CenterEnd)
                                            .clickable(onClick = { email = "" })
                                    )
                                }
                            }
                        })

                    TapTapTextField(
                        value = password,
                        onValueChange = { password = it },
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = FieldMinHeight),
                        shape = TapTapShape.corners.card,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrectEnabled = false,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        decorationBox = { innerTextField ->
                            Box(
                                Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart
                            ) {
                                if (password.isEmpty()) {
                                    Text(
                                        text = "Enter your password",
                                        color = IntlV2Grey60,
                                    )
                                }
                                innerTextField()

                                if (!password.isEmpty()) {
                                    Image(
                                        painter = if (passwordVisible) painterResource(R.drawable.login_password_display_visible) else painterResource(
                                            R.drawable.login_password_display_invisible
                                        ),
                                        contentDescription = "",
                                        Modifier
                                            .align(Alignment.CenterEnd)
                                            .clickable(onClick = {
                                                passwordVisible = !passwordVisible
                                            })
                                    )
                                }
                            }
                        })
                    Text(
                        "Forgot password?",
                        color = TapTapTheme.colors.primary,
                        style = TapTapTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { composeNavigator.navigate(TapTapScreen.ForgotPassword) })

                    Spacer(Modifier.weight(1f))

                    Text(
                        "Log in without password",
                        color = PrimaryTextDisabledMaterialDark,
                        style = TapTapTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.clickable { composeNavigator.navigate(TapTapScreen.LoginWithoutPassword) })
                    DDButton(
                        label = "Log in",
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .imePadding()
                            .padding(bottom = extraSafeBottomPadding()),
                        onPress = {},
                        enable = false
                    )
                }

                1 -> {
                    EmptyStateView(
                        subTextNull = "Write a post to start your profile’s never-ending journey.",
                        painterResourceName = R.drawable.sad_icon_top,
                        modifier = Modifier.padding(start = spacing.gutter)
                    )
                }
            }
        }
    }
}
