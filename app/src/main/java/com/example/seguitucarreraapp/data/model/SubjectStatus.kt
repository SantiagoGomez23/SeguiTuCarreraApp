package com.example.seguitucarreraapp.data.model

enum class SubjectStatus {

    NOT_STARTED,        // Nunca cursada

    IN_PROGRESS,        // Cursando actualmente

    COURSE_APPROVED,    // Cursada aprobada (regular / debe final)

    PROMOTED,           // Promocionada (con nota)

    FINAL_APPROVED      // Final aprobado (con nota)
}
