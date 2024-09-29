package com.example.weatherapp.presentation.auth

sealed class AuthState {
    object Authenticated: AuthState()
    object Registered: AuthState()
    object Unauthenticated: AuthState()
    object Loading: AuthState()
    data class Error(val error: String): AuthState()
}