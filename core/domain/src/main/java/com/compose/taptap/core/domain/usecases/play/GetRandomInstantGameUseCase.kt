package com.compose.taptap.core.domain.usecases.play

import com.compose.taptap.core.domain.repository.PlayRepository
import com.compose.taptap.core.domain.usecases.base.BaseUseCase
import com.compose.taptap.core.model.InstantGameRandomData

class GetRandomInstantGameUseCase(
    private val playRepository: PlayRepository
) : BaseUseCase<Unit, InstantGameRandomData>() {
    override suspend fun execute(input: Unit): InstantGameRandomData {
        return playRepository.getRandomInstantGame()
    }
}
