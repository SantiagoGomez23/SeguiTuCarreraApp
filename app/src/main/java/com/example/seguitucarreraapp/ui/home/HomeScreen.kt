package com.example.seguitucarreraapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seguitucarreraapp.data.model.SubjectStatus
import com.example.seguitucarreraapp.ui.subjects.SubjectsData
import com.example.seguitucarreraapp.ui.subjects.SubjectsViewModel

@Composable
fun HomeScreen(
    viewModel: SubjectsViewModel,
    onGoToMaterias: () -> Unit
) {
    Button(onClick = onGoToMaterias) {
        Text("Ir a Materias")
    }
    val selectedCareerId by viewModel.selectedCareerId.collectAsState()
    val career = SubjectsData.careerById(selectedCareerId)

    val userStatuses by viewModel.userStatuses.collectAsState()

    val subjects = viewModel.subjectsForCurrentCareer
    val total = subjects.size

    val approved = userStatuses.values.count { it.isApproved() }
    val inProgress = userStatuses.values.count { it.status == SubjectStatus.IN_PROGRESS }
    val courseApproved = userStatuses.values.count { it.status == SubjectStatus.COURSE_APPROVED }

    val progress =
        if (total == 0) 0f else approved.toFloat() / total.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
            .padding(24.dp)
    ) {

        Text(
            text = career.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(24.dp))

        CardSection {
            Text("Progreso de la carrera", color = Color(0xFF6B7280))
            Spacer(Modifier.height(8.dp))

            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "$approved de $total materias aprobadas",
                color = Color(0xFF6B7280)
            )
        }
    }
}

@Composable
private fun CardSection(
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(content = content)
    }
}
