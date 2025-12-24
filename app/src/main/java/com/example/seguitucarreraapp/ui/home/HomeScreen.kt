package com.example.seguitucarreraapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.seguitucarreraapp.auth.UserSession
import com.example.seguitucarreraapp.data.local.AppDatabase
import com.example.seguitucarreraapp.data.repository.SubjectRepository

@Composable
fun HomeScreen() {

    val context = LocalContext.current

    // 🔐 Usuario logueado
    val userId = remember { UserSession.uid() }

    // 🧱 DB (Room)
    val database = remember {
        AppDatabase.getInstance(context)

    }

    // 📦 Repository
    val repository = remember {
        SubjectRepository(database.subjectDao())
    }

    // 🧠 ViewModel
    val viewModel = remember {
        HomeViewModel(repository)
    }

    // 🔄 Precarga JSON → Room
    LaunchedEffect(Unit) {
        viewModel.preloadSubjects(context)
    }

    // 📡 Materias de 1° año
    val subjects by viewModel
        .subjectsByYear(1)
        .collectAsState(initial = emptyList())

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

            if (subjects.isEmpty()) {
                Text("No hay materias para mostrar")
            } else {
                LazyColumn {
                    items(subjects) { subject ->
                        Text(
                            text = subject.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
