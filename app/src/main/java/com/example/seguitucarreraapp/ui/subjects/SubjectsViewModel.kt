package com.example.seguitucarreraapp.ui.subjects

import androidx.lifecycle.ViewModel
import com.example.seguitucarreraapp.data.model.Career
import com.example.seguitucarreraapp.data.model.SubjectStatus
import com.example.seguitucarreraapp.data.model.UserSubjectStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class SubjectUi(
    val id: String,
    val name: String,
    val year: Int
)

class SubjectsViewModel : ViewModel() {

    // 🎓 Carrera mock (lista para escalar)
    val currentCareer = Career(
        id = "CAREER_SYS",
        name = "Ingeniería en Sistemas"
    )

    // 📚 Materias mock (años reales)
    val subjects = listOf(
        SubjectUi("ALG_1", "Álgebra I", 1),
        SubjectUi("ANA_1", "Análisis Matemático I", 1),
        SubjectUi("FIS_1", "Física I", 1),
        SubjectUi("PROG_1", "Programación I", 1),

        SubjectUi("MAT_2", "Matemática II", 2),
        SubjectUi("PROG_2", "Programación II", 2),

        SubjectUi("BD_3", "Bases de Datos", 3),
        SubjectUi("SO_3", "Sistemas Operativos", 3),

        SubjectUi("ING_4", "Ingeniería de Software", 4),

        SubjectUi("PROY_5", "Proyecto Final", 5)
    )

    // 👤 Estado del usuario por materia
    private val _userStatuses =
        MutableStateFlow(
            subjects.associate { subject ->
                subject.id to UserSubjectStatus(
                    subjectId = subject.id,
                    careerId = currentCareer.id,
                    status = SubjectStatus.NOT_STARTED
                )
            }
        )

    val userStatuses: StateFlow<Map<String, UserSubjectStatus>> = _userStatuses

    // 🔄 Update estado
    fun updateStatus(
        subjectId: String,
        status: SubjectStatus,
        grade: Int? = null
    ) {
        _userStatuses.update { current ->
            current.toMutableMap().apply {
                put(
                    subjectId,
                    UserSubjectStatus(
                        subjectId = subjectId,
                        careerId = currentCareer.id,
                        status = status,
                        grade = grade
                    )
                )
            }
        }
    }

    // 📆 Años disponibles (DINÁMICOS)
    fun availableYears(): List<Int> =
        subjects.map { it.year }.distinct().sorted()

    // 📚 Materias por año
    fun subjectsByYear(year: Int): List<SubjectUi> =
        subjects.filter { it.year == year }
}
