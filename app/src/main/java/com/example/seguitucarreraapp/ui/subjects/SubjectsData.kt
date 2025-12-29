package com.example.seguitucarreraapp.ui.subjects

import com.example.seguitucarreraapp.data.model.Prerequisite
import com.example.seguitucarreraapp.data.model.PrerequisiteType
import com.example.seguitucarreraapp.ui.subjects.model.Career
import com.example.seguitucarreraapp.ui.subjects.model.Subject

object SubjectsData {

    /* ───── Carreras disponibles ───── */

    val careers = listOf(
        Career(
            id = "lic_informatica",
            name = "Licenciatura en Informática",
            years = 5
        ),
        Career(
            id = "ing_computacion",
            name = "Ingeniería en Computación",
            years = 5
        ),
        Career(
            id = "lic_sistemas",
            name = "Licenciatura en Sistemas",
            years = 5
        )
    )

    fun careerById(id: String): Career =
        careers.firstOrNull { it.id == id } ?: careers.first()

    /* ───── Materias por carrera ───── */

    fun subjectsForCareer(careerId: String): List<Subject> =
        when (careerId) {
            "lic_informatica" -> licInformatica()
            "ing_computacion" -> ingenieriaComputacion()
            "lic_sistemas" -> licenciaturaSistemas()
            else -> emptyList()
        }

    /* ───────── Lic. en Informática ───────── */

    private fun licInformatica() = listOf(

        Subject(
            id = "cadp",
            name = "Conceptos de Algoritmos, Datos y Programas",
            year = 1,
            semester = 1
        ),

        Subject(
            id = "tallerprog",
            name = "Taller de Programación",
            year = 1,
            semester = 2,
            prerequisites = listOf(
                Prerequisite(
                    requiredSubjectId = "cadp",
                    type = PrerequisiteType.COURSE_APPROVED
                )
            )
        ),

        Subject(
            id = "ayed",
            name = "Algoritmos y Estructuras de Datos",
            year = 2,
            semester = 1,
            prerequisites = listOf(
                Prerequisite(
                    requiredSubjectId = "cadp",
                    type = PrerequisiteType.COURSE_APPROVED
                )
            )
        ),

        Subject(
            id = "so",
            name = "Sistemas Operativos",
            year = 4,
            semester = 1,
            prerequisites = listOf(
                Prerequisite(
                    requiredSubjectId = "ayed",
                    type = PrerequisiteType.FINAL_APPROVED
                )
            )
        )
    )

    /* ───────── Ingeniería en Computación ───────── */

    private fun ingenieriaComputacion() = listOf(
        Subject("mat1_ic", "Matemática I", 1, 1),
        Subject("intro_ic", "Introducción a la Computación", 1, 1),
        Subject("fisica1", "Física I", 1, 1),

        Subject("prog1_ic", "Programación I", 1, 2),
        Subject("mat2_ic", "Matemática II", 1, 2),

        Subject("arq_ic", "Arquitectura de Computadoras", 2, 1),
        Subject("eda_ic", "Estructuras de Datos", 2, 1),

        Subject("so_ic", "Sistemas Operativos", 2, 2),
        Subject("bdd_ic", "Bases de Datos", 2, 2),

        Subject("isw_ic", "Ingeniería de Software", 3, 1),
        Subject("redes_ic", "Redes", 3, 1),

        Subject("sd_ic", "Sistemas Distribuidos", 3, 2),

        Subject("pf_ic", "Proyecto Final", 5, 1)
    )

    /* ───────── Licenciatura en Sistemas ───────── */

    private fun licenciaturaSistemas() = listOf(
        Subject("alg_ls", "Algoritmos y Programación", 1, 1),
        Subject("org_ls", "Organización de Computadoras", 1, 1),

        Subject("arq_ls", "Arquitectura de Computadoras", 1, 2),

        Subject("aed_ls", "Algoritmos y Estructuras de Datos", 2, 1),
        Subject("isw1_ls", "Ingeniería de Software I", 2, 2),

        Subject("redes_ls", "Redes", 3, 1),
        Subject("oo2_ls", "Orientación a Objetos II", 3, 2),

        Subject("proyecto_ls", "Proyecto Final", 5, 2)
    )
}
