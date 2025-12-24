package com.example.seguitucarreraapp.data.repository

import com.example.seguitucarreraapp.auth.UserSession
import com.example.seguitucarreraapp.data.local.entity.SubjectEntity
import com.example.seguitucarreraapp.data.SubjectDto

fun SubjectDto.toEntity(): SubjectEntity {
    return SubjectEntity(
        id = id,
        userId = UserSession.uid(),
        name = name,
        year = year
    )
}
