package com.compose.taptap.domain.usecases.base

import com.compose.taptap.data.util.LoadingResult
import com.compose.taptap.data.util.loading
import com.compose.taptap.data.util.loadingFailure
import com.compose.taptap.data.util.loadingSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Created by duc on 15/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

abstract class BaseFlowUseCase<in In, Out> {
    operator fun invoke(parameters: In): Flow<LoadingResult<Out>> =
        execute(parameters).map { loadingSuccess(it) }.onStart { emit(loading()) }
            .catch { emit(loadingFailure(it)) }

    protected abstract fun execute(parameters: In): Flow<Out>
}
