package com.example.seguitucarreraapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.seguitucarreraapp.data.local.entity.SubjectEntity

@Composable
fun SubjectItem(subject: SubjectEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = subject.name,
                style = MaterialTheme.typography.bodyLarge
            )

            if (subject.approved) {
                Text("✔", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
