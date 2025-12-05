package com.compose.taptap.feature.auth.welcome

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

val LocalWelcomeViewModel: ProvidableCompositionLocal<WelcomeViewModel> =
    staticCompositionLocalOf {
        error("No WelcomeViewModel provided in the composition.")
    }
