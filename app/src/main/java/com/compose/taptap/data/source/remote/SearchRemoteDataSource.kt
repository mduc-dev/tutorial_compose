package com.compose.taptap.data.source.remote

import com.compose.taptap.data.network.models.SearchPlaceHolder
import com.compose.taptap.data.network.utils.safeRequest
import com.compose.taptap.domain.repositories.SearchRepository
import com.compose.taptap.domain.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SearchRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : SearchRepository {
    override fun fetchSearchPlaceholder() = httpClient.safeRequest {
        get(Constants.SEARCH_PLACEHOLDER_URL).body<SearchPlaceHolder>()
    }
}
