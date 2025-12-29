package com.example.seguitucarreraapp.data.mapper

import com.example.seguitucarreraapp.data.local.entity.UserSubjectStatusEntity
import com.example.seguitucarreraapp.data.model.UserSubjectStatus

fun UserSubjectStatusEntity.toModel() =
    UserSubjectStatus(
        subjectId = subjectId,
        careerId = careerId,
        status = status,
        grade = grade
    )

fun UserSubjectStatus.toEntity() =
    UserSubjectStatusEntity(
        subjectId = subjectId,
        careerId = careerId,
        status = status,
        grade = grade
    )
