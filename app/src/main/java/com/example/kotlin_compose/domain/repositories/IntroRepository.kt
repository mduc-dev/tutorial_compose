package com.example.kotlin_compose.domain.repositories

import com.example.kotlin_compose.data.network.models.User
import com.example.kotlin_compose.presentation.utils.AuthState
import kotlinx.coroutines.flow.Flow

interface IntroRepository {
    fun getAuthState(): Flow<AuthState>

    suspend fun signInWithGoogle(): Result<User>

    suspend fun signInWithFacebook(): Result<User>

    suspend fun signUp(type: String?, email: String, password: String): Result<User>

    suspend fun signIn(email: String, password: String): Result<User>

    suspend fun signOut(): Result<Unit>
}
