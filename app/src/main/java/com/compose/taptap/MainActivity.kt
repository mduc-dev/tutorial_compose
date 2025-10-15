package com.compose.taptap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.CompositionLocalProvider
import com.compose.taptap.core.navigation.AppComposeNavigator
import com.compose.taptap.core.navigation.LocalComposeNavigator
import com.compose.taptap.core.navigation.TapTapScreen
import com.compose.taptap.ui.TapTapMain
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    internal val composeNavigator: AppComposeNavigator<TapTapScreen> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContent {
            CompositionLocalProvider(
                LocalComposeNavigator provides composeNavigator,
            ) {
                TapTapMain(composeNavigator = composeNavigator)
            }
        }
    }
}

