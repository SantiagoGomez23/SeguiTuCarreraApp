package com.example.seguitucarreraapp.data.repository

import com.example.seguitucarreraapp.data.local.dao.UserSubjectStatusDao
import com.example.seguitucarreraapp.data.mapper.toEntity
import com.example.seguitucarreraapp.data.mapper.toModel
import com.example.seguitucarreraapp.data.model.UserSubjectStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubjectStatusRepository(
    private val dao: UserSubjectStatusDao
) {

    fun observeStatuses(careerId: String): Flow<Map<String, UserSubjectStatus>> =
        dao.observeStatuses(careerId).map { list ->
            list.associateBy(
                { it.subjectId },
                { it.toModel() }
            )
        }

    suspend fun saveStatus(status: UserSubjectStatus) {
        dao.upsert(status.toEntity())
    }
}
