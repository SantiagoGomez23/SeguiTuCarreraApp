package com.example.seguitucarreraapp.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.seguitucarreraapp.data.model.UserSubjectStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseSubjectService {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun userCollection() =
        auth.currentUser?.let { user ->
            firestore
                .collection("users")
                .document(user.uid)
                .collection("subjects")
        }


    suspend fun uploadStatus(status: UserSubjectStatus) {
        val collection = userCollection() ?: return

        collection
            .document(status.subjectId)
            .set(status)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun downloadStatuses(): List<UserSubjectStatus> {
        val collection = userCollection() ?: return emptyList()

        return collection
            .get()
            .await()
            .toObjects(UserSubjectStatus::class.java)
    }

}
