package com.example.seguitucarreraapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seguitucarreraapp.auth.AuthScreen
import com.example.seguitucarreraapp.auth.AuthViewModel
import com.example.seguitucarreraapp.data.local.AppDatabase
import com.example.seguitucarreraapp.ui.subjects.MateriasScreen
import com.example.seguitucarreraapp.ui.subjects.SubjectsViewModel

@Composable
fun NavGraph(
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    // 🗄️ Base de datos
    val database = remember {
        AppDatabase.getInstance(context)
    }

    // 🔑 ViewModel con Room
    val subjectsViewModel = remember {
        SubjectsViewModel()
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

        // 🏠 HOME = Materias
        composable(Routes.Home.route) {
            MateriasScreen(
                viewModel = subjectsViewModel
            )
        }
    }
}
