package com.example.seguitucarreraapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.seguitucarreraapp.data.local.DatabaseProvider
import com.example.seguitucarreraapp.data.repository.SubjectRepository
import com.google.firebase.auth.FirebaseAuth
import kotlin.collections.emptyList

@Composable
fun HomeScreen() {

    val context = LocalContext.current

    // 🔐 Verificamos usuario logueado
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

    // 🧱 Base de datos
    val database = remember {
        DatabaseProvider.getDatabase(context)
    }

    // 📦 Repository
    val repository = remember {
        SubjectRepository(database.subjectDao())
    }

    // 🧠 ViewModel
    val viewModel = remember {
        HomeViewModel(repository)
    }

    // 🔄 Precarga desde JSON (solo una vez y solo si hay usuario)
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            viewModel.preloadSubjects(context)
        }
    }

    // 📡 Observamos materias de 1° año desde Room
    val subjects by viewModel
        .subjectsFirstYear()
        .collectAsState(initial = emptyList())

    // 🎨 UI
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Materias de 1° Año",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (!isLoggedIn) {
                Text("Usuario no logueado")
            } else if (subjects.isEmpty()) {
                Text("No hay materias para mostrar")
            } else {
                LazyColumn {
                    items(subjects) { subject ->
                        SubjectItem(subject = subject)
                    }
                }
            }
        }
    }
}
