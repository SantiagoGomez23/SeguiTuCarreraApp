package com.example.seguitucarreraapp.ui.navigation

import Routes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seguitucarreraapp.auth.AuthScreen
import com.example.seguitucarreraapp.auth.AuthViewModel
import com.example.seguitucarreraapp.data.preferences.CareerPreferences
import com.example.seguitucarreraapp.ui.home.HomeScreen
import com.example.seguitucarreraapp.ui.subjects.MateriasScreen
import com.example.seguitucarreraapp.ui.subjects.SubjectsViewModel

@Composable
fun NavGraph(
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val careerPreferences = remember {
        CareerPreferences(context)
    }

    val subjectsViewModel = remember {
        SubjectsViewModel(careerPreferences)
    }

    val startDestination =
        if (authViewModel.isLoggedIn) Routes.Home.route
        else Routes.Auth.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Routes.Auth.route) {
            AuthScreen(
                authViewModel = authViewModel,
                onAuthSuccess = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Auth.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.Home.route) {
            MateriasScreen(
                viewModel = subjectsViewModel
            )
        }

    }
}

