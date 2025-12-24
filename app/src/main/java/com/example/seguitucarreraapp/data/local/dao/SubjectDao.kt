package com.example.seguitucarreraapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seguitucarreraapp.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(subjects: List<SubjectEntity>)

    // 🔹 FORZAR DB (debug / inspector)
    @Query("SELECT * FROM subjects")
    suspend fun getAllOnce(): List<SubjectEntity>

    // 🔹 Todas las materias del usuario
    @Query("SELECT * FROM subjects WHERE userId = :userId")
    fun getAllByUser(userId: String): Flow<List<SubjectEntity>>

    // ✅ ESTA ES LA CLAVE
    @Query("""
        SELECT * FROM subjects
        WHERE year = :year AND userId = :userId
        ORDER BY name
    """)
    fun getSubjectsByYear(
        year: Int,
        userId: String
    ): Flow<List<SubjectEntity>>
}
