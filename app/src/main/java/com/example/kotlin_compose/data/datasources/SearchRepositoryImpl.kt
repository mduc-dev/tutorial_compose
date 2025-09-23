package com.example.kotlin_compose.data.datasources


import com.example.kotlin_compose.data.network.utils.ApiResult
import com.example.kotlin_compose.data.network.utils.safeRequest
import com.example.kotlin_compose.data.network.models.SearchPlaceHolder
import com.example.kotlin_compose.domain.repositories.SearchRepository
import com.example.kotlin_compose.domain.utils.Constants.SEARCH_PLACEHOLDER_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val httpClient: HttpClient
) : SearchRepository {
    override fun fetchSearchPlaceholder(): Flow<ApiResult<SearchPlaceHolder>> =
        httpClient.safeRequest {
            get(SEARCH_PLACEHOLDER_URL).body<SearchPlaceHolder>()
        }
}