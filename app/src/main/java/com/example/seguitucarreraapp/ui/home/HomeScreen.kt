package com.example.seguitucarreraapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.seguitucarreraapp.ui.subjects.SubjectsViewModel
import com.example.seguitucarreraapp.data.model.SubjectStatus


@Composable
fun HomeScreen(
    viewModel: SubjectsViewModel
) {
    val userStatuses by viewModel.userStatuses.collectAsState()

    val total = viewModel.subjects.size

    val approved = userStatuses.values.count { it.isApproved() }

    val inProgress = userStatuses.values.count {
        it.status == SubjectStatus.IN_PROGRESS
    }

    val courseApproved = userStatuses.values.count {
        it.status == SubjectStatus.COURSE_APPROVED
    }


    val progress =
        if (total == 0) 0f else approved.toFloat() / total.toFloat()

    val message = homeMessage(
        inProgress = inProgress,
        courseApproved = courseApproved,
        approved = approved
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
            .padding(24.dp)
    ) {

        Text(
            text = "Tu carrera",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 📊 PROGRESO GENERAL
        CardSection {
            Text(
                text = "Progreso de la carrera",
                color = Color(0xFF6B7280)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            LinearProgressIndicator(
                progress =  { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color(0xFFE5E7EB)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "$approved de $total materias aprobadas",
                color = Color(0xFF6B7280)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📚 RESUMEN DE ESTADOS
        CardSection {
            Text("📘 Cursando: $inProgress")
            Text("📝 Cursada aprobada: $courseApproved")
            Text("🎓 Materias aprobadas: $approved")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 💬 MENSAJE MOTIVACIONAL
        CardSection(
            background = Color(0xFFEEF2FF)
        ) {
            Text(
                text = message,
                color = Color(0xFF1E3A8A),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/* ───────────── Helpers ───────────── */

@Composable
private fun CardSection(
    background: Color = Color.White,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(background, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(content = content)
    }
}

private fun homeMessage(
    inProgress: Int,
    courseApproved: Int,
    approved: Int
): String =
    when {
        inProgress > 0 ->
            "Estás cursando $inProgress materia${if (inProgress > 1) "s" else ""}. ¡Buen ritmo! 💪"

        courseApproved > 0 ->
            "Tenés $courseApproved materia${if (courseApproved > 1) "s" else ""} con cursada aprobada. ¡A preparar finales! 📚"

        approved > 0 ->
            "Ya aprobaste $approved materia${if (approved > 1) "s" else ""}. Seguí así 🎓"

        else ->
            "Todavía no empezaste materias. ¡Este es un buen momento para arrancar! 🚀"
    }
