package com.example.seguitucarreraapp.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.seguitucarreraapp.data.model.SubjectStatus
import com.example.seguitucarreraapp.data.model.UserSubjectStatus

@Composable
fun SubjectItem(
    subjectName: String,
    userStatus: UserSubjectStatus,
    isLocked: Boolean,
    lockReason: String?,
    onStatusChange: (SubjectStatus, Int?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var editingGrade by remember { mutableStateOf(false) }

    var gradeInput by remember {
        mutableStateOf(userStatus.grade?.toString() ?: "")
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // ───── Animaciones ─────
    val scale by animateFloatAsState(
        targetValue = if (!isLocked) 1f else 0.98f,
        animationSpec = tween(300),
        label = "scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isLocked) 0.4f else 1f,
        animationSpec = tween(300),
        label = "alpha"
    )

    // ───── Foco automático al editar ─────
    LaunchedEffect(editingGrade) {
        if (editingGrade) {
            focusRequester.requestFocus()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .alpha(alpha)
            .clickable(enabled = !isLocked && !editingGrade) {
                expanded = !expanded
            },
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            /* ───── Título ───── */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(subjectName, style = MaterialTheme.typography.bodyLarge)
                if (isLocked) Text("🔒")
            }

            Spacer(Modifier.height(4.dp))

            /* ───── Estado ───── */
            when {
                isLocked && lockReason != null -> {
                    Text(lockReason, style = MaterialTheme.typography.bodySmall)
                }

                userStatus.hasGrade() -> {
                    Text(
                        text = "Nota: ${userStatus.grade}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                else -> {
                    Text(
                        text = statusLabel(userStatus.status),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            /* ───── Acciones ───── */
            if (!isLocked && expanded) {

                Spacer(Modifier.height(12.dp))

                StatusButton("No iniciada") {
                    editingGrade = false
                    onStatusChange(SubjectStatus.NOT_STARTED, null)
                }

                StatusButton("Cursando") {
                    editingGrade = false
                    onStatusChange(SubjectStatus.IN_PROGRESS, null)
                }

                StatusButton("Cursada aprobada") {
                    editingGrade = false
                    onStatusChange(SubjectStatus.COURSE_APPROVED, null)
                }

                StatusButton("Materia promocionada") {
                    editingGrade = true
                    expanded = true
                    onStatusChange(SubjectStatus.PROMOTED, userStatus.grade)
                }

                StatusButton("Final aprobado") {
                    editingGrade = true
                    expanded = true
                    onStatusChange(SubjectStatus.PROMOTED, userStatus.grade)
                }

                /* ───── Ingreso de nota ───── */
                if (editingGrade && userStatus.status == SubjectStatus.PROMOTED) {

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = gradeInput,
                        onValueChange = { input ->
                            if (isValidGradeInput(input)) {
                                gradeInput = input
                            }
                        },
                        label = { Text("Nota (0–10)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                editingGrade = false
                                gradeInput.toIntOrNull()?.let {
                                    onStatusChange(
                                        SubjectStatus.PROMOTED,
                                        it
                                    )
                                }
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )
                }
            }
        }
    }
}

/* ───── Helpers ───── */

@Composable
private fun StatusButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text)
    }
}

private fun statusLabel(status: SubjectStatus): String =
    when (status) {
        SubjectStatus.NOT_STARTED -> "No iniciada"
        SubjectStatus.IN_PROGRESS -> "Cursando"
        SubjectStatus.COURSE_APPROVED -> "Cursada aprobada"
        SubjectStatus.PROMOTED -> "Materia promocionada"
        SubjectStatus.FINAL_APPROVED -> "Final aprobado"
    }

/**
 * Validación de nota:
 * - solo números
 * - rango 0–10
 * - no "00"
 */
private fun isValidGradeInput(input: String): Boolean {
    if (input.isEmpty()) return true
    if (!input.all { it.isDigit() }) return false
    if (input.length > 2) return false
    if (input == "00") return false

    val value = input.toIntOrNull() ?: return false
    return value in 0..10
}
