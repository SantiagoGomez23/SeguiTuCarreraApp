package com.example.seguitucarreraapp.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    onAuthSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }

    val isLoggedIn = authViewModel.isLoggedIn

    // 🔁 Si se loguea, navegamos a Home
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            onAuthSuccess()
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = if (isLogin) "Iniciar sesión" else "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (isLogin) {
                        authViewModel.login(email, password)
                    } else {
                        authViewModel.register(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isLogin) "Entrar" else "Registrarse")
            }


            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { isLogin = !isLogin }) {
                Text(
                    if (isLogin)
                        "¿No tenés cuenta? Registrate"
                    else
                        "¿Ya tenés cuenta? Iniciá sesión"
                )
            }
        }
    }
}
