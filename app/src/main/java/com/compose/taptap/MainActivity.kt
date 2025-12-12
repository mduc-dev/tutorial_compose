package com.compose.taptap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import com.compose.taptap.core.designsystem.component.atoms.video.VideoCacheManager
import com.compose.taptap.ui.TapTapMain

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContent {
          TapTapMain()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        VideoCacheManager.release()
    }
}

