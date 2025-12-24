package com.example.seguitucarreraapp.data.repository

import com.example.seguitucarreraapp.auth.UserSession
import com.example.seguitucarreraapp.data.local.dao.SubjectDao
import com.example.seguitucarreraapp.data.local.entity.SubjectEntity
import com.example.seguitucarreraapp.data.remote.dto.SubjectDto
import kotlinx.coroutines.flow.Flow

class SubjectRepository(
    private val subjectDao: SubjectDao
) {

    // 🔹 Carga inicial desde JSON → Room
    suspend fun preloadSubjectsFromJson(subjects: List<SubjectDto>) {
        val entities = subjects.map { it.toEntity() }
        subjectDao.insertAll(entities)
    }

    // 🔹 Materias por año (UI principal)
    fun getSubjectsByYear(year: Int): Flow<List<SubjectEntity>> {
        val userId = UserSession.uid()
        return subjectDao.getSubjectsByYear(year, userId)
    }

    // 🔹 Todas las materias del usuario (opcional)
    fun getAllSubjects(): Flow<List<SubjectEntity>> {
        val userId = UserSession.uid()
        return subjectDao.getAllByUser(userId)
    }

    // 🔹 Lectura puntual (debug / Database Inspector)
    suspend fun getAllOnce(): List<SubjectEntity> {
        return subjectDao.getAllOnce()
    }
}
