package com.example.seguitucarreraapp.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "subjects",
    primaryKeys = ["id", "userId"]
)
data class SubjectEntity(
    val id: Int,
    val userId: String,
    val name: String,
    val year: Int,
    val approved: Boolean = false,
    val grade: Int? = null
)
