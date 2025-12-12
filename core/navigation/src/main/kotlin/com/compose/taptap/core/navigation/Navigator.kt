package com.compose.taptap.core.navigation
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

interface TapTapNavigator {
    /**
     * Navigate to a new screen by adding it to the back stack.
     */
    fun navigate(screen: TapTapScreen)
    /**
     * Navigate back by removing the top screen from the back stack.
     * @return true if navigation was successful, false if back stack is empty or only has one item.
     */
    fun navigateUp(): Boolean
}



class TapTapNavigatorImpl(
    private val backStack: NavBackStack<NavKey>,
): TapTapNavigator {
    override fun navigate(screen: TapTapScreen) {
        backStack.add(screen)
    }

    override fun navigateUp(): Boolean {
        return if (backStack.size > 1) {
            backStack.removeLastOrNull() != null
        } else {
            false
        }
    }
}
