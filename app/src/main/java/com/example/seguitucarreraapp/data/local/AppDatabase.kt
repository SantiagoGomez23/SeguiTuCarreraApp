package com.example.seguitucarreraapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.seguitucarreraapp.data.local.converter.SubjectStatusConverter
import com.example.seguitucarreraapp.data.local.dao.SubjectDao
import com.example.seguitucarreraapp.data.local.dao.UserSubjectStatusDao
import com.example.seguitucarreraapp.data.local.entity.SubjectEntity
import com.example.seguitucarreraapp.data.local.entity.UserSubjectStatusEntity

@Database(
    entities = [
        SubjectEntity::class,
        UserSubjectStatusEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(SubjectStatusConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun userSubjectStatusDao(): UserSubjectStatusDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "segui_tu_carrera_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
