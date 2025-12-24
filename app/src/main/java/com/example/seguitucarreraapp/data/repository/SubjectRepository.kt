package com.example.seguitucarreraapp.data.repository

import android.content.Context
import com.example.seguitucarreraapp.auth.UserSession
import com.example.seguitucarreraapp.data.local.SubjectLocalDataSource
import com.example.seguitucarreraapp.data.local.dao.SubjectDao
import com.example.seguitucarreraapp.data.local.entity.SubjectEntity
import com.example.seguitucarreraapp.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow

class SubjectRepository(
    private val subjectDao: SubjectDao,
    private val localDataSource: SubjectLocalDataSource = SubjectLocalDataSource()
) {

    suspend fun preloadSubjectsFromJson(context: Context) {
        val subjects = localDataSource.loadSubjects(context)
        subjectDao.insertAll(subjects.map { it.toEntity() })
    }

    fun getSubjectsByYear(year: Int): Flow<List<SubjectEntity>> {
        val userId = UserSession.uid()
        return subjectDao.getSubjectsByYear(year, userId)
    }

    fun getAllSubjects(): Flow<List<SubjectEntity>> {
        val userId = UserSession.uid()
        return subjectDao.getAllByUser(userId)
    }
}
