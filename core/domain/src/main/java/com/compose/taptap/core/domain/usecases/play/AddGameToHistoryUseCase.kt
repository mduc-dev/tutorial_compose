package com.compose.taptap.core.domain.usecases.play

import com.compose.taptap.core.domain.repository.PlayRepository
import com.compose.taptap.core.domain.usecases.base.BaseUseCase
import com.compose.taptap.core.model.InstantGameItem

class AddGameToHistoryUseCase(private val playRepository: PlayRepository) : BaseUseCase<InstantGameItem, Unit>() {
    override suspend fun execute(input: InstantGameItem) {
        playRepository.addToHistory(input)
    }
}
