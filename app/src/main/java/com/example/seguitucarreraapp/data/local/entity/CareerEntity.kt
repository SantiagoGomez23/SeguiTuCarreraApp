package com.example.seguitucarreraapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "careers")
data class CareerEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val years: Int
)
