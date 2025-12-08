package com.compose.taptap.core.domain.usecases.play

import androidx.paging.PagingData
import com.compose.taptap.core.domain.repository.PlayRepository
import com.compose.taptap.core.domain.usecases.base.BaseUseCase
import com.compose.taptap.core.model.InstantGameItem
import kotlinx.coroutines.flow.Flow

class GetPlayGamesFlowUseCase(private val playRepository: PlayRepository): BaseUseCase<Unit, Flow<PagingData<InstantGameItem>>>() {
    override suspend fun execute(input: Unit): Flow<PagingData<InstantGameItem>> {
        return playRepository.fetchInstantGameStream()
    }
}
