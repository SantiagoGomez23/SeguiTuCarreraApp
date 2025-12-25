package com.example.seguitucarreraapp.data.model

/**
 * Estados posibles de una materia para un usuario
 * Modela la realidad universitaria (Argentina-friendly)
 */
enum class SubjectStatus {

    NOT_STARTED,        // Nunca cursada

    IN_PROGRESS,        // Cursando actualmente

    COURSE_APPROVED,    // Cursada aprobada (regular / debe rendir final)

    PROMOTED,           // Promocionada (aprobada sin final, con nota)

    FINAL_APPROVED      // Final aprobado (materia completa, con nota)
}
