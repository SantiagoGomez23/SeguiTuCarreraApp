package com.example.seguitucarreraapp.ui.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InsightCard(insight: Insight) {

    val background = when (insight.type) {
        InsightType.INFO -> Color(0xFFE0F2FE)
        InsightType.WARNING -> Color(0xFFFEF3C7)
        InsightType.SUCCESS -> Color(0xFFDCFCE7)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(background, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(text = insight.icon)
        Spacer(Modifier.width(8.dp))
        Text(
            text = insight.message,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
