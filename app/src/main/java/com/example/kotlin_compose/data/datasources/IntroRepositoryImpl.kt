package com.example.kotlin_compose.data.datasources

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.example.kotlin_compose.data.mappers.toGames
import com.example.kotlin_compose.data.network.models.GamesDto
import com.example.kotlin_compose.data.network.models.User
import com.example.kotlin_compose.data.paging.BasePagingSource
import com.example.kotlin_compose.domain.models.Games
import com.example.kotlin_compose.domain.repositories.GamesRepository
import com.example.kotlin_compose.domain.repositories.IntroRepository
import com.example.kotlin_compose.domain.utils.Constants.HOME_URL
import com.example.kotlin_compose.presentation.utils.AuthState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow

class IntroRepositoryImpl(
    private val httpClient: HttpClient
) : IntroRepository {
    override fun getAuthState(): Flow<AuthState> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle(): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithFacebook(): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(type: String?, email: String, password: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Result<Unit> {
        TODO("Not yet implemented")
    }


}