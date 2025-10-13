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
import com.compose.taptap.navigation.TapMain

class MainActivity : ComponentActivity() {
    internal lateinit var composeNavigator: AppComposeNavigator<TapTapScreen>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContent {
            CompositionLocalProvider(
                LocalComposeNavigator provides composeNavigator,
            ) {
                TapMain(composeNavigator = composeNavigator)
            }
        }
    }
}

