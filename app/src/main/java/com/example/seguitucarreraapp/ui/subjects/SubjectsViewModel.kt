package com.example.seguitucarreraapp.ui.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seguitucarreraapp.data.model.SubjectStatus
import com.example.seguitucarreraapp.data.model.UserSubjectStatus
import com.example.seguitucarreraapp.data.preferences.CareerPreferences
import com.example.seguitucarreraapp.data.repository.SubjectStatusRepository
import com.example.seguitucarreraapp.ui.insights.Insight
import com.example.seguitucarreraapp.ui.insights.InsightType
import com.example.seguitucarreraapp.ui.subjects.model.Career
import com.example.seguitucarreraapp.ui.subjects.model.Subject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SubjectsViewModel(
    private val careerPreferences: CareerPreferences,
    private val repository: SubjectStatusRepository
) : ViewModel() {

    /* ───── Carreras ───── */

    val careers: List<Career> = SubjectsData.careers

    private val _selectedCareerId =
        MutableStateFlow(careerPreferences.getCareerId() ?: careers.first().id)

    val selectedCareerId: StateFlow<String> =
        _selectedCareerId.asStateFlow()

    val selectedCareer: Career
        get() = SubjectsData.careerById(_selectedCareerId.value)

    fun selectCareer(careerId: String) {
        viewModelScope.launch {
            _selectedCareerId.value = careerId
            careerPreferences.saveCareerId(careerId)
        }
    }

    /* ───── Materias ───── */

    val subjectsForCurrentCareer: List<Subject>
        get() = SubjectsData.subjectsForCareer(_selectedCareerId.value)

    fun availableYears(): List<Int> =
        (1..selectedCareer.years).toList()

    fun subjectsByYear(year: Int): List<Subject> =
        subjectsForCurrentCareer.filter { it.year == year }

    /* ───── Estados del usuario (Room) ───── */

    @OptIn(ExperimentalCoroutinesApi::class)
    val userStatuses: StateFlow<Map<String, UserSubjectStatus>> =
        selectedCareerId
            .flatMapLatest { careerId ->
                repository.observeStatuses(careerId)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyMap()
            )

    fun updateStatus(
        subjectId: String,
        status: SubjectStatus,
        grade: Int?
    ) {
        viewModelScope.launch {
            repository.saveStatus(
                UserSubjectStatus(
                    subjectId = subjectId,
                    careerId = selectedCareer.id,
                    status = status,
                    grade = grade
                )
            )
        }
    }

    /* ───── PROGRESO (REACTIVO) ───── */

    val careerProgressFlow: StateFlow<Float> =
        userStatuses
            .map { statuses ->
                val total = subjectsForCurrentCareer.size
                if (total == 0) 0f
                else {
                    statuses.values.count { it.isApproved() }
                        .toFloat() / total
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0f
            )

    val progressByYearFlow: StateFlow<Map<Int, Float>> =
        userStatuses
            .map { statuses ->
                availableYears().associateWith { year ->
                    val subjectsOfYear =
                        subjectsForCurrentCareer.filter { it.year == year }

                    if (subjectsOfYear.isEmpty()) 0f
                    else {
                        val approved = subjectsOfYear.count { subject ->
                            statuses[subject.id]?.isApproved() == true
                        }
                        approved.toFloat() / subjectsOfYear.size
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyMap()
            )

    /* ───── Correlativas ───── */

    fun isSubjectLocked(subject: Subject): Boolean {

        val statuses = userStatuses.value

        if (subject.prerequisites.isEmpty()) return false

        return subject.prerequisites.any { prerequisite ->
            val requiredStatus =
                statuses[prerequisite.requiredSubjectId]
                    ?: return@any true

            !requiredStatus.satisfies(prerequisite.type)
        }
    }

    fun missingPrerequisites(subject: Subject): List<String> {

        val statuses = userStatuses.value

        return subject.prerequisites.mapNotNull { prerequisite ->
            val status = statuses[prerequisite.requiredSubjectId]

            when {
                status == null -> prerequisite.requiredSubjectId
                !status.satisfies(prerequisite.type) -> prerequisite.requiredSubjectId
                else -> null
            }
        }
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

    /* ───── Recomendaciones ───── */

    fun recommendedSubjects(max: Int = 3): List<Subject> {

        val statuses = userStatuses.value

        return subjectsForCurrentCareer
            .filter { subject ->
                val status = statuses[subject.id]

                val notStarted =
                    status == null ||
                            status.status == SubjectStatus.NOT_STARTED

                notStarted && !isSubjectLocked(subject)
            }
            .sortedBy { it.year }
            .take(max)
    }
}
