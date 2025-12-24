package com.example.seguitucarreraapp.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    fun isUserLoggedIn(): Boolean =
        auth.currentUser != null

    suspend fun login(email: String, password: String): Result<Unit> =
        suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { cont.resume(Result.success(Unit)) }
                .addOnFailureListener { cont.resume(Result.failure(it)) }
        }

    suspend fun register(email: String, password: String): Result<Unit> =
        suspendCancellableCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { cont.resume(Result.success(Unit)) }
                .addOnFailureListener { cont.resume(Result.failure(it)) }
        }

    fun logout() {
        auth.signOut()
    }
}
