package com.example.seguitucarreraapp.data.model

data class UserSubjectStatus(
    val subjectId: String,
    val careerId: String,
    val status: SubjectStatus,
    val grade: Int? = null
) {

    fun isApproved(): Boolean {
        return status == SubjectStatus.PROMOTED ||
                status == SubjectStatus.FINAL_APPROVED
    }

    fun requiresGrade(): Boolean {
        return status == SubjectStatus.PROMOTED ||
                status == SubjectStatus.FINAL_APPROVED
    }
}
