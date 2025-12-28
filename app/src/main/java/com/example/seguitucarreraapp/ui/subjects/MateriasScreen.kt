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

@Composable
fun MateriasScreen(
    viewModel: SubjectsViewModel
) {
    val career = viewModel.selectedCareer

    var selectedYear by remember { mutableStateOf(1) }

    val years = viewModel.availableYears()
    val subjects = viewModel.subjectsByYear(selectedYear)

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

        /* ───── SELECTOR DE AÑO ───── */
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(years) { year ->
                    FilterChip(
                        selected = year == selectedYear,
                        onClick = { selectedYear = year },
                        label = {
                            Text(text = "${year}° Año")
                        }
                    )
                }
            }
        }

        /* ───── LISTA DE MATERIAS ───── */
        if (subjects.isEmpty()) {
            item {
                Text("⚠️ No hay materias para este año")
            }
        } else {
            items(subjects) { subject ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = subject.name,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
