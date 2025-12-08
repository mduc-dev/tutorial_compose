package com.compose.taptap.core.domain.usecases.play

import com.compose.taptap.core.domain.repository.PlayRepository
import com.compose.taptap.core.domain.usecases.base.BaseUseCase

class MarkGameAsPlayedUseCase(private val playRepository: PlayRepository) : BaseUseCase<String, Unit>() {
    override suspend fun execute(input: String) {
        playRepository.markPlayed(input)
    }
}
