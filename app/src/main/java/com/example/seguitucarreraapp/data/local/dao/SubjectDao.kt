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

    @Query("""
        SELECT * FROM subjects
        WHERE year = :year AND userId = :userId
        ORDER BY name
    """)
    fun getSubjectsByYear(
        year: Int,
        userId: String
    ): Flow<List<SubjectEntity>>

    @Query("""
        SELECT * FROM subjects
        WHERE userId = :userId
        ORDER BY year, name
    """)
    fun getAllByUser(userId: String): Flow<List<SubjectEntity>>
}
