package com.example.seguitucarreraapp.data.remote

import com.example.seguitucarreraapp.data.remote.SubjectDto

data class SubjectsResponseDto(
    val career: String,
    val plan: String,
    val subjects: List<SubjectDto>
)
