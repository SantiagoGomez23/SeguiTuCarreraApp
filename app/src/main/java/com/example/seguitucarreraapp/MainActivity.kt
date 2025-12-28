package com.example.seguitucarreraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.seguitucarreraapp.ui.theme.SeguiTuCarreraAppTheme
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph
import com.example.seguitucarreraapp.auth.AuthViewModel
import com.example.seguitucarreraapp.data.local.AppDatabase
import com.example.seguitucarreraapp.ui.home.HomeScreen
import com.example.seguitucarreraapp.ui.navigation.NavGraph
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        Log.d("FirebaseTest", "Usuario Actual: ${auth.currentUser}")

        val db = AppDatabase.getInstance(this)

        lifecycleScope.launch {
            db.careerDao().insertAll(
                com.example.seguitucarreraapp.data.local.seed.CareerSeed.careers
            )
        }


        setContent {
            SeguiTuCarreraAppTheme {
                // ViewModel de autenticación compartido
                val authViewModel = viewModel<AuthViewModel>()

                // Navegación principal
                NavGraph(
                    authViewModel = authViewModel
                )
            }
        }
    }
}
