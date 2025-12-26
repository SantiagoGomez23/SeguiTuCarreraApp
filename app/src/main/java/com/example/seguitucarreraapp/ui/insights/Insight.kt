package com.example.seguitucarreraapp.ui.insights

enum class InsightType {
    INFO,
    WARNING,
    SUCCESS
}

data class Insight(
    val icon: String,
    val message: String,
    val type: InsightType
)
