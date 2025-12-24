package com.example.seguitucarreraapp.ui.navigation

sealed class Routes(val route: String) {
    data object Auth : Routes("auth")
    data object Home : Routes("home")
}
