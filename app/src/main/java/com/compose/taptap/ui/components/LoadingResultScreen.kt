package com.compose.taptap.ui.components
import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.taptap.data.util.LoadingResult

@Composable
fun <T> LoadingResultScreen(
    modifier: Modifier = Modifier,
    loadingResult: LoadingResult<T>,
    onRefresh: () -> Unit,
    loadingScreen: @Composable () -> Unit = { LoadingScreen() },
    failureScreen: @Composable (Throwable, Boolean) -> Unit = { _, isLoading ->
        FailureScreen(isLoading, onRefresh)
    },
    content: @Composable (T, Boolean) -> Unit,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = loadingResult,
        contentKey = { it::class.simpleName },
        label = "LoadingComposable",
    ) { result ->
        when (result) {
            LoadingResult.Loading -> loadingScreen()
            is LoadingResult.Failure -> failureScreen(result.throwable, result.isLoading)
            is LoadingResult.Success -> content(result.value, result.isLoading)
        }
    }
}