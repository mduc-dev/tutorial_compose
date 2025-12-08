package com.compose.taptap.core.domain.usecases.play

import com.compose.taptap.core.domain.repository.PlayRepository
import com.compose.taptap.core.domain.usecases.base.BaseUseCase
import com.compose.taptap.core.model.InstantGameItem

class GetGameHistoryUseCase(private val playRepository: PlayRepository) : BaseUseCase<Unit, List<InstantGameItem>>() {
    override suspend fun execute(input: Unit): List<InstantGameItem> {
        return playRepository.getHistory()
    }
}
