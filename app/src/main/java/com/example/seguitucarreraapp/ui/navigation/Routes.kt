package com.example.seguitucarreraapp.ui.navigation

sealed class Routes(val route: String) {
    object Onboarding : Routes("onboarding")
    object Auth : Routes("auth")
    object Home : Routes("home")
}
