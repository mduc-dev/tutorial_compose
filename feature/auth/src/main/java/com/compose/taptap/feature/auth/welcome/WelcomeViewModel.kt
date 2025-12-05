package com.compose.taptap.feature.auth.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.taptap.core.domain.repository.WelcomeRepository
import com.compose.taptap.feature.auth.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val welcomeRepository: WelcomeRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Unauthenticated)
    val uiState = _uiState.asStateFlow()

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        if (welcomeRepository.isUserLoggedIn()) {
            _uiState.value = AuthState.Authenticated
        }
    }

    fun onEvent(event: WelcomeEvent) {
        when (event) {
            is WelcomeEvent.OnLogin -> {
                viewModelScope.launch {
                    welcomeRepository.setUserLoggedIn(true)
                    _uiState.value = AuthState.Authenticated
                }
            }

            is WelcomeEvent.OnGoogleSignIn -> {
                viewModelScope.launch {
                    welcomeRepository.signInWithGoogle().onSuccess {
                        welcomeRepository.setUserLoggedIn(true)
                        _uiState.value = AuthState.Authenticated
                    }.onFailure {
                        // TODO: Handle error
                    }
                }
            }

            is WelcomeEvent.OnFacebookSignIn -> {
                viewModelScope.launch {
                    welcomeRepository.signInWithFacebook().onSuccess {
                        welcomeRepository.setUserLoggedIn(true)
                        _uiState.value = AuthState.Authenticated
                    }.onFailure {
                        // TODO: Handle error
                    }
                }
            }

            is WelcomeEvent.OnSignOut -> {
                viewModelScope.launch {
                    welcomeRepository.signOut().onSuccess {
                        _uiState.value = AuthState.Unauthenticated
                    }
                }
            }
        }
    }
}

sealed class WelcomeEvent {
    data object OnGoogleSignIn : WelcomeEvent()
    data object OnFacebookSignIn : WelcomeEvent()
    data object OnLogin : WelcomeEvent()
    data object OnSignOut : WelcomeEvent()
}
