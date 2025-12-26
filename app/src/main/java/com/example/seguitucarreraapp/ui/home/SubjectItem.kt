package com.example.seguitucarreraapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seguitucarreraapp.data.model.SubjectStatus
import com.example.seguitucarreraapp.data.model.UserSubjectStatus


@Composable
fun SubjectItem(
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
        Column(modifier = Modifier.padding(16.dp)) {

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
                SubjectStatus.entries.forEach { status ->
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

fun statusLabel(status: SubjectStatus): String {
    return when (status) {
        SubjectStatus.NOT_STARTED -> "No cursada"
        SubjectStatus.IN_PROGRESS -> "Cursando"
        SubjectStatus.COURSE_APPROVED -> "Cursada aprobada (dar final)"
        SubjectStatus.PROMOTED -> "Promocionada"
        SubjectStatus.FINAL_APPROVED -> "Final aprobado"
    }
}

fun statusColor(status: SubjectStatus): Color {
    return when (status) {
        SubjectStatus.NOT_STARTED -> Color.Gray
        SubjectStatus.IN_PROGRESS -> Color(0xFF2563EB) // azul
        SubjectStatus.COURSE_APPROVED -> Color(0xFFF59E0B) // amarillo
        SubjectStatus.PROMOTED -> Color(0xFF16A34A) // verde
        SubjectStatus.FINAL_APPROVED -> Color(0xFF15803D) // verde oscuro
    }
}

