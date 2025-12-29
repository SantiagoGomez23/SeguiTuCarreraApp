package com.example.seguitucarreraapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seguitucarreraapp.auth.AuthScreen
import com.example.seguitucarreraapp.auth.AuthViewModel
import com.example.seguitucarreraapp.data.local.AppDatabase
import com.example.seguitucarreraapp.data.preferences.CareerPreferences
import com.example.seguitucarreraapp.data.remote.FirebaseSubjectService
import com.example.seguitucarreraapp.data.repository.SubjectStatusRepository
import com.example.seguitucarreraapp.ui.subjects.MateriasScreen
import com.example.seguitucarreraapp.ui.subjects.SubjectsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NavGraph(
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    // ───── DataStore ─────
    val careerPreferences = remember {
        CareerPreferences(context)
    }

    // ───── Room ─────
    val database = remember {
        AppDatabase.getInstance(context)
    }

    // ───── Firebase ─────
    val firebaseService = remember {
        FirebaseSubjectService()
    }


    // ───── Repository ─────
    val subjectStatusRepository = remember {
        SubjectStatusRepository(
            dao = database.userSubjectStatusDao(),
            firebase = firebaseService
        )
    }

    // ───── ViewModel ─────
    val subjectsViewModel = remember {
        SubjectsViewModel(
            careerPreferences = careerPreferences,
            repository = subjectStatusRepository
        )
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
