package com.example.kotlin_compose.data.source.remote

import com.example.kotlin_compose.data.network.models.SearchPlaceHolder
import com.example.kotlin_compose.data.network.utils.safeRequest
import com.example.kotlin_compose.domain.repositories.SearchRepository
import com.example.kotlin_compose.domain.utils.Constants
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
