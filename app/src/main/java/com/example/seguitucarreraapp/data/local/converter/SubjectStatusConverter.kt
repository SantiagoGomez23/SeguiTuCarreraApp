package com.example.seguitucarreraapp.data.local.converter

import androidx.room.TypeConverter
import com.example.seguitucarreraapp.data.model.SubjectStatus

class SubjectStatusConverter {

    @TypeConverter
    fun fromStatus(status: SubjectStatus): String =
        status.name

    @TypeConverter
    fun toStatus(value: String): SubjectStatus =
        SubjectStatus.valueOf(value)
}
