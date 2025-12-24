package com.example.seguitucarreraapp.data.mapper

import com.example.seguitucarreraapp.auth.UserSession
import com.example.seguitucarreraapp.data.local.entity.SubjectEntity
import com.example.seguitucarreraapp.data.remote.SubjectDto

fun SubjectDto.toEntity(): SubjectEntity {
    return SubjectEntity(
        code = code,
        userId = UserSession.uid(),
        name = name,
        year = year
    )
}
