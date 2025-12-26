package com.example.seguitucarreraapp.ui.subjects

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.seguitucarreraapp.ui.statistics.StatisticsSection

@Composable
fun MateriasScreen(
    viewModel: SubjectsViewModel
) {
    val userStatuses by viewModel.userStatuses.collectAsState()

    val years = viewModel.availableYears()
    var selectedYear by remember { mutableStateOf(years.first()) }

    val subjectsByYear = viewModel.subjectsByYear(selectedYear)
    val subjectsBySemester = subjectsByYear.groupBy { it.semester }

    // ───── PROGRESO GLOBAL ─────
    val totalSubjects = viewModel.subjects.size
    val approvedSubjects = userStatuses.values.count { it.isApproved() }
    val progress =
        if (totalSubjects == 0) 0f
        else approvedSubjects.toFloat() / totalSubjects.toFloat()

    // ───── PROMEDIO ─────
    val gradedSubjects = userStatuses.values.filter { it.hasGrade() }
    val average =
        if (gradedSubjects.isEmpty()) null
        else gradedSubjects.mapNotNull { it.grade }.average()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        /* ───── SALUDO ───── */
        item {
            Column {
                Text(
                    text = "¡Hola!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Así va tu progreso en ${viewModel.currentCareer.name}",
                    color = Color(0xFF6B7280)
                )
            }
        }

        /* ───── PROGRESO + PROMEDIO ───── */
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Progreso de la carrera",
                        color = Color(0xFF6B7280)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$approvedSubjects de $totalSubjects materias aprobadas",
                        color = Color(0xFF6B7280)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (average != null) {
                        Text(
                            text = "Promedio: ${String.format("%.2f", average)}",
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text(
                            text = "Promedio: —",
                            color = Color(0xFF9CA3AF)
                        )
                    }
                }
            }
        }

        /* ───── TABS DE AÑO ───── */
        item {
            YearTabs(
                years = years,
                selectedYear = selectedYear,
                onYearSelected = { selectedYear = it }
            )
        }

        /* ───── MATERIAS POR SEMESTRE ───── */
        subjectsBySemester.forEach { (semester, subjects) ->

            item {
                Text(
                    text = if (semester == 1) "1° Semestre" else "2° Semestre",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(subjects) { subject ->
                val status = userStatuses[subject.id]
                    ?: UserSubjectStatus(
                        subjectId = subject.id,
                        careerId = viewModel.currentCareer.id,
                        status = SubjectStatus.NOT_STARTED
                    )

                SubjectItem(
                    subjectName = subject.name,
                    userStatus = status,
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

        /* ───── ESTADÍSTICAS ───── */
        item {
            Spacer(modifier = Modifier.height(24.dp))
            StatisticsSection(
                progressByYear = viewModel.progressByYear()
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun YearTabs(
    years: List<Int>,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        years.forEach { year ->
            val selected = year == selectedYear

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (selected)
                    MaterialTheme.colorScheme.primary
                else
                    Color(0xFFE5E7EB),
                tonalElevation = if (selected) 2.dp else 0.dp,
                modifier = Modifier
                    .clickable { onYearSelected(year) }
            ) {
                Text(
                    text = "${year}° Año",
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                    color = if (selected) Color.White else Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}