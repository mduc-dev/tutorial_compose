package com.compose.taptap.core.data.repository.welcome

import com.compose.taptap.core.model.User
import kotlinx.coroutines.flow.Flow

interface WelcomeRepository {
    fun getAuthState(): Flow<AuthState>

    suspend fun signInWithGoogle(): Result<Unit> //replace temporary Unit with User

    suspend fun signInWithFacebook(): Result<Unit> //replace temporary Unit with User

    suspend fun signUp(type: String?, email: String, password: String): Result<User>

    suspend fun signIn(email: String, password: String): Result<User>

    suspend fun signOut(): Result<Unit>
}