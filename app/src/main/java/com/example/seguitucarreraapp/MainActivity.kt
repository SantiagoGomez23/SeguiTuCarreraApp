package com.example.seguitucarreraapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seguitucarreraapp.auth.AuthViewModel
import com.example.seguitucarreraapp.ui.navigation.NavGraph
import com.example.seguitucarreraapp.ui.theme.SeguiTuCarreraAppTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔐 Firebase Auth (solo debug)
        val auth = FirebaseAuth.getInstance()
        Log.d("FirebaseTest", "Usuario actual: ${auth.currentUser}")

        setContent {
            SeguiTuCarreraAppTheme {

                // ViewModel de autenticación
                val authViewModel: AuthViewModel = viewModel()

                // Navegación principal
                NavGraph(
                    authViewModel = authViewModel
                )
            }
        }
    }
}
