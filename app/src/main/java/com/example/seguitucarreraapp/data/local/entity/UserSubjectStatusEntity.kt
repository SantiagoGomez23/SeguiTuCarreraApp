package com.example.seguitucarreraapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.seguitucarreraapp.data.model.SubjectStatus

@Entity(tableName = "user_subject_status")
data class UserSubjectStatusEntity(
    @PrimaryKey val subjectId: String,
    val careerId: String,
    val status: SubjectStatus,
    val grade: Int?
)
