package com.example.seguitucarreraapp.ui.subjects

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
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
import com.example.seguitucarreraapp.ui.SectionDivider
import com.example.seguitucarreraapp.ui.home.SubjectItem
import com.example.seguitucarreraapp.ui.insights.InsightCard
import com.example.seguitucarreraapp.ui.statistics.StatisticsSection

@Composable
fun MateriasScreen(
    viewModel: SubjectsViewModel
) {
    /* ───── Estado local ───── */
    var selectedYear by remember { mutableStateOf(1) }
    val userStatuses by viewModel.userStatuses.collectAsState()
    val insights = viewModel.getInsights()

    val career = viewModel.currentCareer
    val subjectsOfYear = viewModel.subjectsByYear(selectedYear)
    val subjectsBySemester = subjectsOfYear.groupBy { it.semester }

    val progressByYear = viewModel.progressByYear()
    val careerProgress = progressByYear.values.average().toFloat()

    val approvedCount = userStatuses.values.count { it.isApproved() }

    val averageGrade = userStatuses.values
        .mapNotNull { it.grade }
        .takeIf { it.isNotEmpty() }
        ?.average()

    /* ───── Animación saludo ───── */
    var showGreeting by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showGreeting = true
    }

    val userName = "Santiago"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
            .padding(top = 12.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        /* ───── Saludo animado ───── */
        item {
            AnimatedVisibility(
                visible = showGreeting,
                enter = slideInVertically(
                    initialOffsetY = { -40 },
                    animationSpec = tween(400)
                ) + fadeIn(animationSpec = tween(400))
            ) {
                Column {
                    Text(
                        text = "¡Hola, $userName! 👋",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Así va tu progreso en ${career.name}",
                        color = Color(0xFF6B7280)
                    )
                }
            }
        }

        /* ───── Progreso general ───── */
        item {
            Card(shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(16.dp)) {

                    Text("Progreso de la carrera", color = Color(0xFF6B7280))

                    Text(
                        text = "${(careerProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    LinearProgressIndicator(
                        progress = { careerProgress },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        "$approvedCount de ${viewModel.subjects.size} materias aprobadas",
                        color = Color(0xFF6B7280)
                    )

                    Text(
                        averageGrade?.let {
                            "Promedio: ${"%.2f".format(it)}"
                        } ?: "Promedio: —",
                        color = Color(0xFF6B7280)
                    )
                }
            }
        }

        /* ───── Tabs de años ───── */
        item {
            YearTabs(
                years = viewModel.availableYears(),
                selectedYear = selectedYear,
                onYearSelected = { }
            )
        }

        /* ───── Materias ───── */
        subjectsBySemester.forEach { (semester, subjects) ->

            item {
                Text(
                    text = "$semester° Semestre",
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

        item {
            SectionDivider()
        }

        /* ───── Insights ───── */
        if (insights.isNotEmpty()) {
            item {
                Text(
                    text = "💡 Datos interesantes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(insights) { insight ->
                InsightCard(insight = insight)
            }
        }

        item {
            SectionDivider()
        }

        /* ───── Estadísticas ───── */
        item {
            StatisticsSection(
                progressByYear = progressByYear
            )
        }
    }
}

/* ───── YearTabs ───── */

@Composable
fun YearTabs(
    years: List<Int>,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(years) { year ->
            FilterChip(
                selected = year == selectedYear,
                onClick = { onYearSelected(year) },
                label = { Text("${year}° Año") }
            )
        }
    }
}
