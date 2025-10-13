package com.compose.taptap.ui.launcher.welcome

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.taptap.domain.repositories.WelcomeRepository
import com.compose.taptap.ui.utils.AuthState
import com.compose.taptap.ui.utils.Provider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.core.content.edit

class WelcomeViewModel(
    private val welcomeRepository: WelcomeRepository, private val prefs: SharedPreferences
) : ViewModel() {

    companion object {
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    }

    private val _welcomeUiState = MutableStateFlow<AuthState>(AuthState.Idle)

    val welcomeUiState = _welcomeUiState.asStateFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _welcomeUiState.value = AuthState.Error(exception.message ?: "Unknown error")
    }

    init {
        // Check initial auth state, e.g. from SharedPreferences or Firebase
        checkInitialAuthState()
    }

    private fun checkInitialAuthState() {
        // Check if user is logged in from SharedPreferences or other storage
        val isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        Log.i("VM", "checkInitialAuthState → $isLoggedIn")

        if (isLoggedIn) {
            _welcomeUiState.value = AuthState.Authenticated
        } else {
            _welcomeUiState.value = AuthState.Unauthenticated
        }
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        welcomeRepository.signIn(email, password).onSuccess { data ->
            _welcomeUiState.value = AuthState.Authenticated
        }
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        welcomeRepository.signUp("", email, password).onSuccess { data ->
            _welcomeUiState.value = AuthState.Authenticated
        }
    }

    fun signInWithFaceBook() = viewModelScope.launch(coroutineExceptionHandler) {
        _welcomeUiState.value = AuthState.Loading(Provider.Facebook)
        welcomeRepository.signInWithFacebook().onSuccess { data ->
            prefs.edit { putBoolean(KEY_IS_LOGGED_IN, true) }
            _welcomeUiState.value = AuthState.Authenticated
        }
    }

    fun signInWithGoogle() = viewModelScope.launch(coroutineExceptionHandler) {
        _welcomeUiState.value = AuthState.Loading(Provider.Google)
        welcomeRepository.signInWithGoogle().onSuccess { data ->
            prefs.edit { putBoolean(KEY_IS_LOGGED_IN, true) }
            _welcomeUiState.value = AuthState.Authenticated
        }
    }

    fun signOut() = viewModelScope.launch(coroutineExceptionHandler) {
        _welcomeUiState.value = AuthState.Unauthenticated
        welcomeRepository.signOut()
    }
}