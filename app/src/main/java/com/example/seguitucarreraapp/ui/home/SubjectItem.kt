package com.example.seguitucarreraapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.seguitucarreraapp.data.model.SubjectStatus
import com.example.seguitucarreraapp.data.model.UserSubjectStatus
import androidx.compose.foundation.layout.FlowRow

@Composable
fun SubjectItem(
    subjectName: String,
    userStatus: UserSubjectStatus,
    isLocked: Boolean = false,
    lockReason: String? = null,
    onStatusChange: (SubjectStatus, Int?) -> Unit
) {
    val focusManager = LocalFocusManager.current

    var isEditingGrade by remember { mutableStateOf(false) }
    var pendingStatus by remember { mutableStateOf<SubjectStatus?>(null) }
    var gradeText by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isLocked) Color(0xFFE5E7EB) else Color.White
        )
    ) {
        Column(Modifier.padding(16.dp)) {

            Text(
                text = subjectName,
                fontWeight = FontWeight.SemiBold
            )

            if (isLocked && lockReason != null) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = lockReason,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280)
                )
            }

            Spacer(Modifier.height(12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                StatusButton(
                    "Cursando",
                    userStatus.status == SubjectStatus.IN_PROGRESS,
                    !isLocked
                ) {
                    onStatusChange(SubjectStatus.IN_PROGRESS, null)
                }

                StatusButton(
                    "Cursada aprobada",
                    userStatus.status == SubjectStatus.COURSE_APPROVED,
                    !isLocked
                ) {
                    onStatusChange(SubjectStatus.COURSE_APPROVED, null)
                }

                StatusButton(
                    "Final aprobado",
                    userStatus.status == SubjectStatus.FINAL_APPROVED,
                    !isLocked
                ) {
                    pendingStatus = SubjectStatus.FINAL_APPROVED
                    isEditingGrade = true
                    gradeText = ""
                }

                StatusButton(
                    "Promocionada",
                    userStatus.status == SubjectStatus.PROMOTED,
                    !isLocked
                ) {
                    pendingStatus = SubjectStatus.PROMOTED
                    isEditingGrade = true
                    gradeText = ""
                }
            }

            if (!isEditingGrade && userStatus.hasGrade()) {
                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Nota: ${userStatus.grade}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }


            if (isEditingGrade && pendingStatus != null && !isLocked) {
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = gradeText,
                    onValueChange = { value ->
                        if (value.isEmpty()) {
                            gradeText = ""
                            return@OutlinedTextField
                        }
                        if (!value.all { it.isDigit() }) return@OutlinedTextField
                        if (value.length > 2) return@OutlinedTextField
                        if (value.startsWith("0") && value.length > 1) return@OutlinedTextField

                        val number = value.toIntOrNull() ?: return@OutlinedTextField
                        if (number !in 0..10) return@OutlinedTextField

                        gradeText = value
                    },
                    label = { Text("Nota (0–10)") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            val grade = gradeText.toIntOrNull()
                            if (grade != null && pendingStatus != null) {
                                onStatusChange(pendingStatus!!, grade)
                            }
                            isEditingGrade = false
                            pendingStatus = null
                            focusManager.clearFocus()
                        }
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun StatusButton(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        enabled = enabled,
        onClick = onClick,
        label = { Text(text) }
    )
}
