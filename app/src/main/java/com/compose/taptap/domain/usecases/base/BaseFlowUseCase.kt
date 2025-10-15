package com.compose.taptap.domain.usecases.base

import kotlinx.coroutines.flow.Flow

/**
 * Created by duc on 15/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

abstract class BaseFlowUseCase<In,Out> {
    abstract fun execute(input: In): Flow<Out>
}