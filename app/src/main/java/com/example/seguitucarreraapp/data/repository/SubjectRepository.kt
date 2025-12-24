package com.example.seguitucarreraapp.data.repository

import android.content.Context
import com.example.seguitucarreraapp.data.local.dao.SubjectDao
import com.example.seguitucarreraapp.data.mapper.toEntity
import com.example.seguitucarreraapp.data.remote.SubjectLocalDataSource
import kotlinx.coroutines.flow.Flow
import com.example.seguitucarreraapp.data.local.entity.SubjectEntity

class SubjectRepository(
    private val subjectDao: SubjectDao
) {

    private val localDataSource = SubjectLocalDataSource()

    suspend fun preloadSubjectsFromJson(context: Context) {
        val subjects = localDataSource.loadSubjects(context)
        subjectDao.insertAll(subjects.map { it.toEntity() })
    }

    fun getSubjectsByYear(year: Int, userId: String): Flow<List<SubjectEntity>> {
        return subjectDao.getSubjectsByYear(year, userId)
    }
}
