package com.example.kotlin_compose.data.datasources

import android.content.SharedPreferences
import com.example.kotlin_compose.data.network.models.User
import com.example.kotlin_compose.domain.repositories.WelcomeRepository
import com.example.kotlin_compose.presentation.utils.AuthState
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import androidx.core.content.edit


class WelcomeRepositoryImpl(
    private val httpClient: HttpClient?, private val prefs: SharedPreferences
) : WelcomeRepository {
    companion object {
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    }

    override fun getAuthState(): Flow<AuthState> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle(): Result<Unit> {
        return try {
            delay(500)
            prefs.edit { putBoolean(KEY_IS_LOGGED_IN, true) }
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun signInWithFacebook(): Result<Unit> {
        return try {
            delay(500)
            prefs.edit { putBoolean(KEY_IS_LOGGED_IN, true) }
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun signUp(type: String?, email: String, password: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            prefs.edit { remove(KEY_IS_LOGGED_IN) }
            Result.success(Unit)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}