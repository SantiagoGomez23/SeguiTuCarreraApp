package com.example.seguitucarreraapp.ui.insights

data class Insight(
    val icon: String,
    val message: String,
    val type: InsightType
)

enum class InsightType {
    INFO,
    WARNING,
    SUCCESS
}
