package com.example.seguitucarreraapp.ui.insights

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seguitucarreraapp.ui.insights.InsightType.*

@Composable
fun InsightsSection(
    insights: List<Insight>
) {
    if (insights.isEmpty()) return

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "💡 Datos interesantes",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        insights.forEach { insight ->
            InsightCard(insight)
        }
    }
}
@Composable
fun InsightCard(insight: Insight) {
    val background = when (insight.type) {
        InsightType.INFO -> Color(0xFFE0F2FE)
        InsightType.WARNING -> Color(0xFFFEF3C7)
        InsightType.SUCCESS -> Color(0xFFDCFCE7)
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = insight.icon,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = insight.message,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun insightColors(type: InsightType): Pair<Color, Color> {
    return when (type) {
        INFO ->
            Color(0xFFDBEAFE) to Color(0xFF1D4ED8)   // azul

        WARNING ->
            Color(0xFFFEF3C7) to Color(0xFFB45309)   // amarillo

        SUCCESS ->
            Color(0xFFD1FAE5) to Color(0xFF065F46)   // verde
    }
}


