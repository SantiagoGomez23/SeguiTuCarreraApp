package com.example.seguitucarreraapp.ui.subjects

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.seguitucarreraapp.data.model.SubjectStatus
import com.example.seguitucarreraapp.data.model.UserSubjectStatus
import com.example.seguitucarreraapp.data.preferences.CareerPreferences
import com.example.seguitucarreraapp.ui.insights.Insight
import com.example.seguitucarreraapp.ui.insights.InsightType
import com.example.seguitucarreraapp.ui.subjects.model.Career
import com.example.seguitucarreraapp.ui.subjects.model.Subject

class SubjectsViewModel(careerPreferences: CareerPreferences) : ViewModel() {

    /* ───── Carreras ───── */

    val careers: List<Career> = SubjectsData.careers

    private val _selectedCareerId =
        MutableStateFlow(SubjectsData.careers.first().id)

    val selectedCareerId: StateFlow<String> =
        _selectedCareerId.asStateFlow()

    val selectedCareer: Career
        get() = SubjectsData.careerById(_selectedCareerId.value)

    fun selectCareer(careerId: String) {
        _selectedCareerId.value = careerId
    }

    /* ───── Materias ───── */

    val subjectsForCurrentCareer: List<Subject>
        get() = SubjectsData.subjectsForCareer(_selectedCareerId.value)

    fun availableYears(): List<Int> =
        (1..selectedCareer.years).toList()

    fun subjectsByYear(year: Int): List<Subject> =
        subjectsForCurrentCareer.filter { it.year == year }

    /* ───── Estados del usuario ───── */

    private val _userStatuses =
        MutableStateFlow<Map<String, UserSubjectStatus>>(emptyMap())

    val userStatuses: StateFlow<Map<String, UserSubjectStatus>> =
        _userStatuses.asStateFlow()

    fun updateStatus(
        subjectId: String,
        status: SubjectStatus,
        grade: Int?
    ) {
        val updated = _userStatuses.value.toMutableMap()

        updated[subjectId] = UserSubjectStatus(
            subjectId = subjectId,
            careerId = _selectedCareerId.value,
            status = status,
            grade = grade
        )

        _userStatuses.value = updated
    }

    /* ───── Progreso ───── */

    fun approvedCount(): Int =
        userStatuses.value.values.count { it.isApproved() }

    fun progress(): Float {
        val total = subjectsForCurrentCareer.size
        if (total == 0) return 0f
        return approvedCount().toFloat() / total
    }

    /* ───── Insights ───── */

    fun getInsights(): List<Insight> {
        val insights = mutableListOf<Insight>()

        val pendingFinals = userStatuses.value.values.count {
            it.status == SubjectStatus.COURSE_APPROVED
        }

        if (pendingFinals > 0) {
            insights.add(
                Insight(
                    icon = "📌",
                    message = "Tenés $pendingFinals materias con cursada aprobada pendientes de final",
                    type = InsightType.WARNING
                )
            )
        }

        return insights
    }
}
