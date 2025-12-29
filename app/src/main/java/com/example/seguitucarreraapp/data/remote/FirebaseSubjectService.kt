package com.example.seguitucarreraapp.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.seguitucarreraapp.data.model.SubjectStatus
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
            .set(
                mapOf(
                    "careerId" to status.careerId,
                    "status" to status.status.name,
                    "grade" to status.grade,
                    "updatedAt" to System.currentTimeMillis()
                )
            )
            .await()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun downloadStatuses(): List<UserSubjectStatus> {
        val collection = userCollection() ?: return emptyList()

        val snapshot = collection.get().await()

        return snapshot.documents.mapNotNull { doc ->

            val subjectId = doc.id
            val careerId = doc.getString("careerId") ?: return@mapNotNull null
            val statusString = doc.getString("status") ?: return@mapNotNull null
            val grade = doc.getLong("grade")?.toInt()

            UserSubjectStatus(
                subjectId = subjectId,
                careerId = careerId,
                status = SubjectStatus.valueOf(statusString),
                grade = grade
            )
        }
    }


}
