package com.compose.taptap.core.domain.usecases.search

import com.compose.taptap.core.domain.repository.SearchRepository
import com.compose.taptap.core.domain.usecases.base.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow
import com.compose.taptap.core.model.Search

/**
 * Created by duc on 15/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

class GetSearchPlaceholderFlowUseCase(
    private val searchRepository: SearchRepository
) : BaseFlowUseCase<Unit, Search>() {

    override fun execute(
        parameters: Unit
    ): Flow<Search> {
        return searchRepository.fetchSearchPlaceholder()
    }
}
