package com.example.seguitucarreraapp.ui.subjects.model

import com.example.seguitucarreraapp.data.model.Prerequisite

data class Career(
    val id: String,
    val name: String,
    val years: Int
)

data class Subject(
    val id: String,
    val name: String,
    val year: Int,
    val semester: Int,
    val prerequisites: List<Prerequisite> = emptyList()
)
