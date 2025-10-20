package com.compose.taptap.core.domain.usecases.game

import com.compose.taptap.core.data.repository.game.GamesRepository
import com.compose.taptap.core.domain.usecases.base.BaseFlowUseCase
import com.compose.taptap.core.model.ListGameItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by duc on 15/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

class GetGameFlowUseCase(
    private val repository: GamesRepository,
) : BaseFlowUseCase<Unit, List<ListGameItem>>() {

    override fun execute(parameters: Unit): Flow<List<ListGameItem>> =
        repository.getGames().map { it.getOrThrow() }

    fun refresh(): Flow<Unit> = repository.refreshGames().map { }
}