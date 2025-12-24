package com.example.seguitucarreraapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.seguitucarreraapp.data.local.DatabaseProvider
import com.example.seguitucarreraapp.data.repository.SubjectRepository
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen() {

    LaunchedEffect(Unit) {
        println("HOME SCREEN EJECUTADA")
    }

    val context = LocalContext.current

    // 🔐 Verificamos si hay usuario logueado
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

    // 1️⃣ Creamos la base de datos (solo una vez)
    val database = remember {
        DatabaseProvider.getDatabase(context)
    }

    // 2️⃣ Creamos el repository
    val repository = remember {
        SubjectRepository(database.subjectDao())
    }

    // 3️⃣ Creamos el ViewModel
    val viewModel = remember {
        HomeViewModel(repository)
    }

    // 4️⃣ Cargamos datos SOLO si hay usuario
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            viewModel.preloadSubjects(context)
        }
    }

    // 5️⃣ UI mínima visible
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(text = "Home Screen")

            Spacer(modifier = Modifier.height(8.dp))

            if (isLoggedIn) {
                Text(text = "Usuario logueado. Cargando materias...")
            } else {
                Text(text = "No hay usuario logueado")
            }
        }
    }
}
