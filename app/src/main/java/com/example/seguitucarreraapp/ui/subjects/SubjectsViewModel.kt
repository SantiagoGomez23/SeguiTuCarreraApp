package com.example.seguitucarreraapp.ui.subjects

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.seguitucarreraapp.data.model.SubjectStatus
import com.example.seguitucarreraapp.data.model.UserSubjectStatus

// Modelo simple de materia (si ya tenés uno, usá el tuyo)
data class Subject(
    val id: String,
    val name: String,
    val year: Int,
    val semester: Int // 1 o 2
)


// Modelo simple de carrera (si ya tenés uno, usá el tuyo)
data class Career(
    val id: String,
    val name: String,
    val years: Int
)

class SubjectsViewModel : ViewModel() {

    // Carrera actual (mock por ahora)
    val currentCareer = Career(
        id = "systems",
        name = "Lic. en Informatica",
        years = 5
    )

    // Materias (mock por ahora)
    val subjects: List<Subject> = listOf(

        // ───── 1° AÑO – 1° SEMESTRE ─────
        Subject("cadp", "Conceptos de Algoritmos, Datos y Programas", 1, 1),
        Subject("orgcomp", "Organización de Computadoras", 1, 1),
        Subject("mat1", "Matemática 1", 1, 1),

        // ───── 1° AÑO – 2° SEMESTRE ─────
        Subject("tallerprog", "Taller de Programación", 1, 2),
        Subject("arqcomp", "Arquitectura de Computadoras", 1, 2),
        Subject("mat2", "Matemática 2", 1, 2),

        // ───── 2° AÑO – 1° SEMESTRE ─────
        Subject("fod", "Fundamentos de Organización de Datos", 2, 1),
        Subject("ayed", "Algoritmos y Estructuras de Datos", 2, 1),
        Subject("seminario", "Seminario de Lenguajes", 2, 1),
        Subject("mat3", "Matemática 3", 2, 1),

        // ───── 2° AÑO – 2° SEMESTRE ─────
        Subject("bdd", "Diseño de Bases de Datos", 2, 2),
        Subject("introso", "Introducción a los Sistemas Operativos", 2, 2),
        Subject("oo1", "Orientación a Objetos 1", 2, 2),

        // ───── 3° AÑO – 1° SEMESTRE ─────
        Subject("ingsoft1", "Ingeniería de Software 1", 3, 1),
        Subject("paradigmas", "Conceptos y Paradigmas de Lenguajes de Programación", 3, 1),
        Subject("redes", "Redes y Comunicaciones", 3, 1),

        // ───── 3° AÑO – 2° SEMESTRE ─────
        Subject("oo2", "Orientación a Objetos 2", 3, 2),
        Subject("concurrente", "Programación Concurrente", 3, 2),
        Subject("labsoft", "Laboratorio de Software", 3, 2),

        // ───── 4° AÑO – 1° SEMESTRE ─────
        Subject("so", "Sistemas Operativos", 4, 1),
        Subject("computabilidad", "Computabilidad y Complejidad", 4, 1),

        // ───── 4° AÑO – 2° SEMESTRE ─────
        Subject("distribuida", "Programación Distribuida y Tiempo Real", 4, 2),
        Subject("ux", "Diseño de Experiencia de Usuario", 4, 2),
        Subject("mat4", "Matemática 4", 4, 2),

        // ───── 5° AÑO ─────
        Subject("proyecto", "Proyecto de Software", 5, 1),
        Subject("aspectos", "Aspectos Sociales y Profesionales de Informática", 5, 1),
        Subject("tesina", "Tesina de Licenciatura", 5, 2)
    )



    // Estado del usuario por materia
    private val _userStatuses =
        MutableStateFlow<Map<String, UserSubjectStatus>>(emptyMap())

    val userStatuses: StateFlow<Map<String, UserSubjectStatus>> = _userStatuses

    // 🔄 Actualizar estado de una materia
    fun updateStatus(
        subjectId: String,
        status: SubjectStatus,
        grade: Int?
    ) {
        val updated = _userStatuses.value.toMutableMap()

        updated[subjectId] = UserSubjectStatus(
            subjectId = subjectId,
            careerId = currentCareer.id,
            status = status,
            grade = grade
        )

        _userStatuses.value = updated
    }

    // 📅 Años disponibles según la carrera
    fun availableYears(): List<Int> =
        (1..currentCareer.years).toList()

    // 📘 Materias filtradas por año
    fun subjectsByYear(year: Int): List<Subject> =
        subjects.filter { it.year == year }

    // 📊 PROGRESO POR AÑO (ESTA ERA LA FUNCIÓN QUE FALTABA)
    fun progressByYear(): Map<Int, Float> {
        val years = (1..currentCareer.years).toList()

        return years.associateWith { year ->
            val subjectsOfYear = subjects.filter { it.year == year }

            if (subjectsOfYear.isEmpty()) {
                0f
            } else {
                val approved = subjectsOfYear.count { subject ->
                    userStatuses.value[subject.id]?.isApproved() == true
                }
                approved.toFloat() / subjectsOfYear.size.toFloat()
            }
        }
    }

}
