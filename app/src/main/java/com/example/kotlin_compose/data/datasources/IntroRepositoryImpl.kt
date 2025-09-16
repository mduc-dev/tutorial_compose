package com.example.kotlin_compose.data.datasources

import com.example.kotlin_compose.data.network.models.User
import com.example.kotlin_compose.domain.repositories.WelcomeRepository
import com.example.kotlin_compose.presentation.utils.AuthState
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

class WelcomeRepositoryImpl(
    private val httpClient: HttpClient
) : WelcomeRepository {
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