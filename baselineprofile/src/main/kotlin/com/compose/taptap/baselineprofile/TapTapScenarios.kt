package com.compose.taptap.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.tapTapScenarios() {
    // -----------------
    // Welcome Screen
    // -----------------
    waitForWelcomeContent()
    
    // -----------------
    // Auth Flow (If waiting for welcome succeeds)
    // -----------------
    // We try to go to login, but we don't block if we are already logged in (scenario dependent)
    // For baseline profile, we want to exercise code paths.
    // If we are logged in, waitForWelcomeContent might time out or fail if we didn't handle it.
    // Ideally, baseline profile runs on a fresh install or controlled state.
    // Assuming fresh install for the heavy startup optimization:
    startUpAuthFlow()
    
    // -----------------
    // Main Flow (If logged in)
    // -----------------
    // In a real scenario, we might need to handle login or mock it.
    // For now, we include the scenarios so they are compiled and ready.
    // If the app starts logged in (e.g. cached token), this will run.
    startUpMainFlow()
}

fun MacrobenchmarkScope.waitForWelcomeContent() = device.apply {
    // Wait for "Log in" button on welcome screen
    wait(Until.hasObject(By.text("Log in")), 5_000L)
}

fun MacrobenchmarkScope.startUpAuthFlow() = device.apply {
    val loginButton = findObject(By.text("Log in"))
    if (loginButton != null) {
        loginButton.click()
        // Wait for Login Screen content, e.g. "Welcome back" or email field
        wait(Until.hasObject(By.textContains("Welcome")), 5_000L)
        pressBack() // Go back to welcome for next iteration or just finish
    }
}

fun MacrobenchmarkScope.startUpMainFlow() = device.apply {
    // Wait for Game Screen (Home)
    // We added testTag="game_list" to LazyColumn in GameScreen. 
    // UiAutomator uses resource-id for testTags in Compose.
    val gameList = wait(Until.findObject(By.res("game_list")), 5_000L)
    
    if (gameList != null) {
        gameList.setGestureMargin(device.displayWidth / 5)
        gameList.scroll(Direction.DOWN, 1f)
        gameList.scroll(Direction.UP, 1f)
    }

    // Switch to Play Tab (Bottom Bar)
    // Assuming BottomNavigationItem text or content description
    val playTab = findObject(By.text("Play"))
    playTab?.click()
    
    val playGrid = wait(Until.findObject(By.res("play_grid")), 5_000L)
    if (playGrid != null) {
        playGrid.scroll(Direction.DOWN, 1f)
    }

    // Switch to Me Tab
    val meTab = findObject(By.text("Me"))
    meTab?.click()
    
    val meList = wait(Until.findObject(By.res("me_list")), 5_000L)
    if (meList != null) {
        meList.scroll(Direction.DOWN, 1f)
    }
}

/**
 * Waits until an object with [selector] if visible on screen and returns the object.
 * If the element is not available in [timeout], throws [AssertionError]
 */
internal fun UiDevice.waitAndFindObject(selector: BySelector, timeout: Long = 15_000L): UiObject2 {
    if (!wait(Until.hasObject(selector), timeout)) {
        throw AssertionError("Element not found on screen in ${timeout}ms (selector=$selector)")
    }

    return findObject(selector)
}
