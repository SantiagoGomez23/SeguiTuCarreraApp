package com.example.seguitucarreraapp.data.repository

import com.example.seguitucarreraapp.data.local.dao.UserSubjectStatusDao
import com.example.seguitucarreraapp.data.mapper.toEntity
import com.example.seguitucarreraapp.data.mapper.toModel
import com.example.seguitucarreraapp.data.model.UserSubjectStatus
import com.example.seguitucarreraapp.data.remote.FirebaseSubjectService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubjectStatusRepository(
    private val dao: UserSubjectStatusDao,
    private val firebase: FirebaseSubjectService
) {

    fun observeStatuses(careerId: String): Flow<Map<String, UserSubjectStatus>> =
        dao.observeStatuses(careerId).map { list ->
            list.associateBy(
                { it.subjectId },
                { it.toModel() }
            )
        }

    suspend fun saveStatus(status: UserSubjectStatus) {
        // 1️⃣ Guardar local (Room)
        dao.upsert(status.toEntity())

        // 2️⃣ Sincronizar remoto (Firebase)
        firebase.uploadStatus(status)
    }

    suspend fun restoreFromFirebase() {
        val remoteStatuses = firebase.downloadStatuses()
        remoteStatuses.forEach { status ->
            dao.upsert(status.toEntity())
        }
    }
}
