package com.compose.taptap.core.data.repository.search

import com.compose.taptap.core.domain.repository.SearchRepository
import com.compose.taptap.core.model.Search
import com.compose.taptap.core.network.service.TapTapService
import com.compose.taptap.core.data.repository.base.BaseRepository
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(private val tapTapService: TapTapService) : BaseRepository(),
    SearchRepository {
    override fun fetchSearchPlaceholder(): Flow<Search> {
        return safeFlow {
            val response = tapTapService.getSearchPlaceholder()
            response.data
        }
    }
}
