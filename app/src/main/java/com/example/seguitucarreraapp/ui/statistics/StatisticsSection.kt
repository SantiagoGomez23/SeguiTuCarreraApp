package com.example.seguitucarreraapp.ui.statistics

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsSection(
    progressByYear: Map<Int, Float>
) {
    Text(
        text = "📊 Estadísticas",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

    if (progressByYear.isNotEmpty()) {
        YearProgressChart(
            progressByYear = progressByYear
        )
    } else {
        Text(
            text = "Todavía no hay datos suficientes para mostrar estadísticas.",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
