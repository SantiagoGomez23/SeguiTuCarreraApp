package com.example.seguitucarreraapp.ui.statistics

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun YearProgressChart(
    progressByYear: Map<Int, Float>
) {
    val sorted = progressByYear.toSortedMap()

    val entries = sorted.entries.mapIndexed { index, entry ->
        FloatEntry(
            x = index.toFloat(),
            y = entry.value * 100f
        )
    }

    val entryModel = entryModelOf(entries)

    Chart(
        chart = columnChart(),
        model = entryModel,
        startAxis = rememberStartAxis(
            valueFormatter = { value, _ -> "${value.toInt()}%" }
        ),
        bottomAxis = rememberBottomAxis(
            valueFormatter = { value, _ ->
                sorted.keys.elementAtOrNull(value.toInt())
                    ?.let { "${it}°" } ?: ""
            }
        ),
        modifier = Modifier
            .height(240.dp)
            .padding(top = 8.dp)
    )
}
