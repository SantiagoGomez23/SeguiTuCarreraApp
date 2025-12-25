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

@Composable
fun MateriasScreen(
    viewModel: SubjectsViewModel
) {
    val userStatuses by viewModel.userStatuses.collectAsState()
    val years = viewModel.availableYears()

    var selectedYear by remember { mutableStateOf(years.first()) }

    val subjectsByYear = viewModel.subjectsByYear(selectedYear)

    // 📊 Cálculos para progreso
    val totalSubjects = viewModel.subjects.size
    val approvedSubjects = userStatuses.values.count { it.isApproved() }

    val progress =
        if (totalSubjects == 0) 0f
        else approvedSubjects.toFloat() / totalSubjects.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
            .padding(16.dp)
    ) {

        /* ───────── SALUDO ───────── */

        Text(
            text = "¡Hola!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Así va tu progreso en ${viewModel.currentCareer.name}",
            color = Color(0xFF6B7280)
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* ───────── PROGRESO ───────── */

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
                        .height(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color(0xFFE5E7EB)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$approvedSubjects de $totalSubjects materias aprobadas",
                    color = Color(0xFF6B7280)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        /* ───────── TABS POR AÑO ───────── */

        YearTabs(
            years = years,
            selectedYear = selectedYear,
            onYearSelected = { selectedYear = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* ───────── LISTA DE MATERIAS ───────── */

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(subjectsByYear) { subject ->

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
    }
}

/* ───────── Tabs por año ───────── */

@Composable
private fun YearTabs(
    years: List<Int>,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        years.forEach { year ->
            val selected = year == selectedYear

            Box(
                modifier = Modifier
                    .background(
                        if (selected)
                            MaterialTheme.colorScheme.primary
                        else
                            Color(0xFFE5E7EB),
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { onYearSelected(year) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "${year}° Año",
                    color = if (selected) Color.White else Color.Black
                )
            }
        }
    }
}

/* ───────── Item materia ───────── */

@Composable
private fun SubjectItem(
    subjectName: String,
    userStatus: UserSubjectStatus,
    onStatusChange: (SubjectStatus, Int?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var gradeText by remember { mutableStateOf(userStatus.grade?.toString() ?: "") }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {

            Text(
                text = subjectName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = statusLabel(userStatus.status),
                color = statusColor(userStatus.status),
                modifier = Modifier.clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                SubjectStatus.values().forEach { status ->
                    DropdownMenuItem(
                        text = { Text(statusLabel(status)) },
                        onClick = {
                            expanded = false
                            gradeText = ""
                            onStatusChange(status, null)
                        }
                    )
                }
            }

            if (userStatus.requiresGrade()) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = gradeText,
                    onValueChange = {
                        gradeText = it
                        onStatusChange(
                            userStatus.status,
                            it.toIntOrNull()
                        )
                    },
                    label = { Text("Nota") },
                    singleLine = true,
                    modifier = Modifier.width(120.dp)
                )
            }
        }
    }
}

/* ───────── Helpers ───────── */

private fun statusLabel(status: SubjectStatus): String =
    when (status) {
        SubjectStatus.NOT_STARTED -> "No cursada"
        SubjectStatus.IN_PROGRESS -> "Cursando"
        SubjectStatus.COURSE_APPROVED -> "Cursada aprobada (dar final)"
        SubjectStatus.PROMOTED -> "Promocionada"
        SubjectStatus.FINAL_APPROVED -> "Final aprobado"
    }

private fun statusColor(status: SubjectStatus): Color =
    when (status) {
        SubjectStatus.NOT_STARTED -> Color.Gray
        SubjectStatus.IN_PROGRESS -> Color(0xFF2563EB)
        SubjectStatus.COURSE_APPROVED -> Color(0xFFF59E0B)
        SubjectStatus.PROMOTED -> Color(0xFF16A34A)
        SubjectStatus.FINAL_APPROVED -> Color(0xFF15803D)
    }
