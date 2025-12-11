package com.compose.taptap.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.tapTapScenarios() {
    // Determine which screen we are on
    // Welcome screen has "Continue with Google", "Continue with Facebook", "Sign up", "Log in"
    // We prioritize "Continue with Google" as the primary login action for this benchmark
    // because it is mocked and fast (no actual credential entry needed in this dev setup).
    val welcomeIndicator = device.findObject(By.text("Continue with Google"))
    val gamesTab = device.findObject(By.text("Games"))

    if (welcomeIndicator != null) {
        // We are on Welcome Screen -> Perform Login -> Run Main Scenarios
        performLoginAndRunMainScenarios()
    } else if (gamesTab != null) {
        // We are already on Main Screen -> Run Main Scenarios directly
        runMainScenarios()
    } else {
        // Fallback: If we can't find either immediately, wait for one.
        if (device.wait(Until.hasObject(By.text("Continue with Google")), 5_000L)) {
            performLoginAndRunMainScenarios()
        } else if (device.wait(Until.hasObject(By.text("Games")), 5_000L)) {
            runMainScenarios()
        }
    }
}

fun MacrobenchmarkScope.performLoginAndRunMainScenarios() = device.apply {
    // 1. Click "Continue with Google" to log in
    // Confirmed in WelcomeRepositoryImpl that this simulates a successful login after 500ms
    val googleLogin = findObject(By.text("Continue with Google"))
    if (googleLogin != null) {
        googleLogin.click()
        
        // 2. Wait for transition to Main Screen (tab "Games")
        // This measures the performance of the login state transition + main screen startup
        if (wait(Until.hasObject(By.text("Games")), 10_000L)) {
            runMainScenarios()
        } else {
             throw AssertionError("Login failed or timed out: 'Games' tab not found after clicking 'Continue with Google'")
        }
    }
}

fun MacrobenchmarkScope.runMainScenarios() = device.apply {
    // -----------------
    // Games Tab (Home)
    // -----------------
    // Make sure we are on Games tab
    val gamesTab = findObject(By.text("Games"))
    gamesTab?.click()
    
    // Use testTag "game_list" as resource ID
    // Note: With testTagsAsResourceId = true, the resource name is the tag.
    // We use a regex or specific match. UiAutomator maps testTag to resource-id.
    val gameList = wait(Until.findObject(By.res("game_list")), 5_000L)
    if (gameList != null) {
        gameList.setGestureMargin(device.displayWidth / 5)
        gameList.scroll(Direction.DOWN, 1f)
        gameList.scroll(Direction.UP, 1f)
    }

    // -----------------
    // Play Tab
    // -----------------
    val playTab = findObject(By.text("Play"))
    playTab?.click()
    
    val playGrid = wait(Until.findObject(By.res("play_grid")), 5_000L)
    if (playGrid != null) {
        playGrid.setGestureMargin(device.displayWidth / 5)
        playGrid.scroll(Direction.DOWN, 1f)
        playGrid.scroll(Direction.UP, 1f)
    }

    // -----------------
    // You Tab
    // -----------------
    val youTab = findObject(By.text("You"))
    youTab?.click()
    
    val meList = wait(Until.findObject(By.res("me_list")), 5_000L)
    if (meList != null) {
        meList.setGestureMargin(device.displayWidth / 5)
        meList.scroll(Direction.DOWN, 1f)
        meList.scroll(Direction.UP, 1f)
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
