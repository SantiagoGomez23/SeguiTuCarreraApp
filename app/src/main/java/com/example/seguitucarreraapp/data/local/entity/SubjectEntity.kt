package com.example.seguitucarreraapp.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "subjects",
    primaryKeys = ["code", "userId"]
)
data class SubjectEntity(
    val code: String,
    val userId: String,
    val name: String,
    val year: Int,
    val approved: Boolean = false,
    val grade: Int? = null
)
