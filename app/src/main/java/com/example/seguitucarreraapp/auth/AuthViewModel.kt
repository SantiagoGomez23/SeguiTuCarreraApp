package com.example.seguitucarreraapp.auth

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    var isLoggedIn by mutableStateOf(repository.isUserLoggedIn())
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
                .onSuccess { isLoggedIn = true }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            repository.register(email, password)
                .onSuccess { isLoggedIn = true }
        }
    }

    fun logout() {
        repository.logout()
        isLoggedIn = false
    }
}
