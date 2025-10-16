package com.compose.taptap.core.domain.usecases.game

import com.compose.taptap.data.model.ListItem
import com.compose.taptap.data.repository.GamesRepository
import com.compose.taptap.domain.usecases.base.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by duc on 15/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */


class GetGameFlowUseCase(
    private val repository: GamesRepository,
) : BaseFlowUseCase<Unit, List<ListItem>>() {

    override fun execute(parameters: Unit): Flow<List<ListItem>> =
        repository.getGameFlow().map { it.getOrThrow() }

    suspend fun refresh(): Flow<Unit> = repository.refreshGames().map { }
}