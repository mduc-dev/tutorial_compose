package com.compose.taptap.domain.usecases.search

import com.compose.taptap.data.network.models.SearchPlaceHolder
import com.compose.taptap.data.network.utils.ApiResult
import com.compose.taptap.domain.repositories.SearchRepository
import com.compose.taptap.domain.usecases.base.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow

/**
 * Created by duc on 15/10/25
 *
 * Copyright © 2025 mduc. All rights reserved.
 */

//TODO: now it not working, check again to can be fetch
class FetchSearchPlaceholderUseCase(
    private val searchRepository: SearchRepository
) : BaseFlowUseCase<Unit, ApiResult<SearchPlaceHolder>>() {

    override fun execute(input: Unit): Flow<ApiResult<SearchPlaceHolder>> {
        return searchRepository.fetchSearchPlaceholder()
    }
}