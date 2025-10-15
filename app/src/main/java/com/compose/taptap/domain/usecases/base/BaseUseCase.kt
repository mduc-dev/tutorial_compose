package com.compose.taptap.domain.usecases.base

/**
 * Created by duc on 15/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

abstract class BaseUseCase<In, Out> {
    abstract suspend fun execute(input: In): Out
}