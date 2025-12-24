package com.example.seguitucarreraapp.auth

import com.google.firebase.auth.FirebaseAuth

object UserSession {

    fun uid(): String {
        return FirebaseAuth.getInstance().currentUser?.uid
            ?: throw IllegalStateException("Usuario no autenticado")
    }
}
