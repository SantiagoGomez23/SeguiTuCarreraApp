package com.example.seguitucarreraapp.data.mapper

import com.example.seguitucarreraapp.auth.UserSession
import com.example.seguitucarreraapp.data.SubjectDto
import com.example.seguitucarreraapp.data.local.entity.SubjectEntity

fun SubjectDto.toEntity(): SubjectEntity {
    return SubjectEntity(
        name = name,
        year = year,
        approved = false,
        grade = null,
        userId = UserSession.uid()
    )
}
