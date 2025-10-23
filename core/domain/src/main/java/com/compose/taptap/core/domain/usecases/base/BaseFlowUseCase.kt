package com.compose.taptap.core.domain.usecases.base

import kotlinx.coroutines.flow.Flow

/**
 * Created by duc on 15/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

abstract class BaseFlowUseCase<in In, Out> {
    abstract fun execute(parameters: In): Flow<Out>
}
