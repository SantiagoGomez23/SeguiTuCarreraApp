package com.example.seguitucarreraapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seguitucarreraapp.data.model.SubjectStatus
import com.example.seguitucarreraapp.data.model.UserSubjectStatus
import com.example.seguitucarreraapp.data.model.requiresGrade

@Composable
fun SubjectItem(
    subjectName: String,
    userStatus: UserSubjectStatus,
    onStatusChange: (SubjectStatus, Int?) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }

    var gradeText by remember(userStatus.grade) {
        mutableStateOf(userStatus.grade?.toString() ?: "")
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                // 👉 cerrar input al tocar fuera
                focusManager.clearFocus()
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = subjectName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
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

                            val requires = status.requiresGrade()
                            gradeText = if (requires) gradeText else ""

                            onStatusChange(
                                status,
                                if (requires) gradeText.toIntOrNull() else null
                            )

                            focusManager.clearFocus()
                        }
                    )
                }
            }

            if (userStatus.status.requiresGrade()) {
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = gradeText,
                    onValueChange = { newValue ->
                        val filtered = filterGradeInput(newValue)

                        if (filtered != gradeText) {
                            gradeText = filtered
                            onStatusChange(
                                userStatus.status,
                                filtered.toIntOrNull()
                            )
                        }
                    },
                    label = { Text("Nota (0 a 10)") },
                    singleLine = true,
                    modifier = Modifier.width(160.dp)
                )
            }
        }
    }
}

/* ───── Helpers ───── */

fun statusLabel(status: SubjectStatus): String =
    when (status) {
        SubjectStatus.NOT_STARTED -> "No cursada"
        SubjectStatus.IN_PROGRESS -> "Cursando"
        SubjectStatus.COURSE_APPROVED -> "Cursada aprobada (dar final)"
        SubjectStatus.PROMOTED -> "Promocionada"
        SubjectStatus.FINAL_APPROVED -> "Final aprobado"
    }

fun statusColor(status: SubjectStatus): Color =
    when (status) {
        SubjectStatus.NOT_STARTED -> Color(0xFF6B7280)
        SubjectStatus.IN_PROGRESS -> Color(0xFF2563EB)
        SubjectStatus.COURSE_APPROVED -> Color(0xFFF59E0B)
        SubjectStatus.PROMOTED -> Color(0xFF16A34A)
        SubjectStatus.FINAL_APPROVED -> Color(0xFF15803D)
    }

/* ───── Validación Nota ───── */

private fun filterGradeInput(input: String): String {
    if (input.isEmpty()) return ""

    if (!input.all { it.isDigit() }) return ""

    if (input == "0") return "0"
    if (input.startsWith("0")) return "0"

    val number = input.toIntOrNull() ?: return ""

    return when {
        number > 10 -> "10"
        else -> number.toString()
    }
}
