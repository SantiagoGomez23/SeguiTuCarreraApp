package com.example.seguitucarreraapp.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seguitucarreraapp.auth.AuthScreen
import com.example.seguitucarreraapp.auth.AuthViewModel
import com.example.seguitucarreraapp.data.preferences.OnboardingPreferences
import com.example.seguitucarreraapp.ui.home.HomeScreen
import com.example.seguitucarreraapp.ui.onboarding.OnboardingScreen
import com.example.seguitucarreraapp.ui.onboarding.OnboardingViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NavGraph(authViewModel: AuthViewModel) {

    val navController = rememberNavController()
    val context = LocalContext.current

    val onboardingPrefs = remember { OnboardingPreferences(context) }

    var onboardingCompleted by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        onboardingPrefs.onboardingCompleted.collect { completed ->
            onboardingCompleted = completed
            isLoading = false
        }
    }

    if (isLoading) {
        // Podés poner un SplashScreen si querés
        return
    }

    val startDestination = remember {
        when {
            !onboardingCompleted -> Routes.Onboarding.route
            authViewModel.isLoggedIn -> Routes.Home.route
            else -> Routes.Auth.route
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Routes.Onboarding.route) {

            val onboardingViewModel =
                remember { OnboardingViewModel(onboardingPrefs) }

            OnboardingScreen(
                onFinish = {
                    onboardingViewModel.completeOnboarding()
                    navController.navigate(Routes.Auth.route) {
                        popUpTo(Routes.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

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
            HomeScreen()
        }
    }
}
