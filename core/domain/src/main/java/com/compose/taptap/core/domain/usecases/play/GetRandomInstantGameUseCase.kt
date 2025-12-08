package com.compose.taptap.core.domain.usecases.play

import com.compose.taptap.core.domain.repository.PlayRepository
import com.compose.taptap.core.domain.usecases.base.BaseUseCase
import com.compose.taptap.core.model.InstantGameRandomResponse

class GetRandomInstantGameUseCase(
    private val playRepository: PlayRepository
) : BaseUseCase<Unit, InstantGameRandomResponse>() {
    override suspend fun execute(input: Unit): InstantGameRandomResponse {
        return playRepository.getRandomInstantGame()
    }
}
