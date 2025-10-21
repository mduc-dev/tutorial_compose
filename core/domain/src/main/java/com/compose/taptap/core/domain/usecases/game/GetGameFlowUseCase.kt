package com.compose.taptap.core.domain.usecases.game

import androidx.paging.PagingData
import com.compose.taptap.core.data.repository.game.GamesRepository
import com.compose.taptap.core.domain.usecases.base.BaseFlowUseCase
import com.compose.taptap.core.model.ListGameItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by duc on 15/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

class GetGameFlowUseCase(
    private val repository: GamesRepository,
) : BaseFlowUseCase<Unit, PagingData<ListGameItem>>() {

    override fun execute(parameters: Unit): Flow<PagingData<ListGameItem>> =
        repository.gamesStream()
}
