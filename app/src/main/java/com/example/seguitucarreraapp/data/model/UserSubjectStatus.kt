package com.example.seguitucarreraapp.data.model

data class UserSubjectStatus(
    val subjectId: String,
    val careerId: String,
    val status: SubjectStatus,
    val grade: Int? = null
) {

    /**
     * Una materia cuenta como APROBADA
     * solo si está PROMOTED
     * (promoción o final aprobado)
     */
    fun isApproved(): Boolean {
        return status == SubjectStatus.PROMOTED
    }

    /**
     * Requiere nota solo si está PROMOTED
     */
    fun requiresGrade(): Boolean {
        return status == SubjectStatus.PROMOTED
    }

    /**
     * Tiene nota válida
     */
    fun hasGrade(): Boolean {
        return status == SubjectStatus.PROMOTED && grade != null
    }

    /**
     * Verifica correlativas
     */
    fun satisfies(prerequisiteType: PrerequisiteType): Boolean {
        return when (prerequisiteType) {
            PrerequisiteType.COURSE_APPROVED ->
                status == SubjectStatus.COURSE_APPROVED ||
                        status == SubjectStatus.PROMOTED

            PrerequisiteType.FINAL_APPROVED ->
                status == SubjectStatus.PROMOTED
        }
    }
}
