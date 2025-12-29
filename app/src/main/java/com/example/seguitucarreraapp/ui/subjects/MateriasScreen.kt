package com.example.seguitucarreraapp.ui.subjects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seguitucarreraapp.data.model.SubjectStatus
import com.example.seguitucarreraapp.data.model.UserSubjectStatus
import com.example.seguitucarreraapp.ui.home.SubjectItem
import com.example.seguitucarreraapp.ui.subjects.components.YearProgressBars

@Composable
fun MateriasScreen(
    viewModel: SubjectsViewModel
) {
    val career = viewModel.selectedCareer

    val userStatuses by viewModel.userStatuses.collectAsState()
    val careerProgress by viewModel.careerProgressFlow.collectAsState()
    val progressByYear by viewModel.progressByYearFlow.collectAsState()

    var selectedYear by remember { mutableStateOf(1) }

    val years = viewModel.availableYears()
    val subjectsOfYear = viewModel.subjectsByYear(selectedYear)

    val subjectsBySemester =
        subjectsOfYear.groupBy { it.semester }.toSortedMap()

    val approvedCount = userStatuses.values.count { it.isApproved() }
    val totalSubjects = viewModel.subjectsForCurrentCareer.size

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        /* ───── TÍTULO ───── */
        item {
            Text(
                text = career.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        /* ───── PROGRESO GENERAL ───── */
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Text(
                        text = "Progreso de la carrera",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "${(careerProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { careerProgress },
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "$approvedCount de $totalSubjects materias aprobadas",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        /* ───── SELECTOR DE AÑO ───── */
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(years) { year ->
                    FilterChip(
                        selected = year == selectedYear,
                        onClick = { selectedYear = year },
                        label = { Text("${year}° Año") }
                    )
                }
            }
        }

        /* ───── MATERIAS POR SEMESTRE ───── */
        if (subjectsBySemester.isEmpty()) {

            item {
                Text("⚠️ No hay materias para este año")
            }

        } else {

            subjectsBySemester.forEach { (semester, subjects) ->

                item {
                    Text(
                        text = "$semester° Semestre",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                items(subjects) { subject ->

                    val status =
                        userStatuses[subject.id]
                            ?: UserSubjectStatus(
                                subjectId = subject.id,
                                careerId = career.id,
                                status = SubjectStatus.NOT_STARTED,
                                grade = null
                            )

                    val isLocked = viewModel.isSubjectLocked(subject)
                    val missing = viewModel.missingPrerequisites(subject)

                    val lockReason =
                        if (isLocked && missing.isNotEmpty()) {
                            "Requiere: ${missing.joinToString()}"
                        } else null

                    SubjectItem(
                        subjectName = subject.name,
                        userStatus = status,
                        isLocked = isLocked,
                        lockReason = lockReason,
                        onStatusChange = { newStatus, grade ->
                            viewModel.updateStatus(
                                subjectId = subject.id,
                                status = newStatus,
                                grade = grade
                            )
                        }
                    )
                }
            }
        }

        /* ───── PROGRESO POR AÑO ───── */
        item {
            Column {
                Text(
                    text = "Progreso por año",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(8.dp))

                YearProgressBars(
                    progressByYear = progressByYear
                )
            }
        }

        /* ───── RECOMENDACIONES ───── */
        val recommendations = viewModel.recommendedSubjects()

        if (recommendations.isNotEmpty()) {

            item {
                Text(
                    text = "💡 Recomendado para cursar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(recommendations) { subject ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            text = subject.name,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "${subject.year}° año · ${subject.semester}° semestre",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }
        }
    }
}
