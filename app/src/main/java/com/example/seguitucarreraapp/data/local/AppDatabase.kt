package com.example.seguitucarreraapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.seguitucarreraapp.data.local.dao.SubjectDao
import com.example.seguitucarreraapp.data.local.entity.SubjectEntity

@Database(
    entities = [SubjectEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
}
