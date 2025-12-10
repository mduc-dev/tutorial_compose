package com.compose.taptap.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() = baselineProfileRule.collect(
        packageName = "com.compose.taptap",
        includeInStartupProfile = true
    ) {
        pressHome()
        startActivityAndWait()

        // Navigate to Me Screen
        // Assuming there is a Bottom Navigation Bar with "Me" or "Profile" text
        val meTab = device.findObject(By.text("Me"))
        if (meTab != null) {
            meTab.click()
            device.waitForIdle()
            
            // Scroll a bit on Me Screen
            val list = device.findObject(By.scrollable(true))
            if (list != null) {
                list.fling(androidx.test.uiautomator.Direction.DOWN)
                device.waitForIdle()
            }
        }
    }
}
