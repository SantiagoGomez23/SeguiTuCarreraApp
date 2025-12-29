package com.example.seguitucarreraapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seguitucarreraapp.data.local.entity.CareerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CareerDao {

    @Query("SELECT * FROM careers")
    fun observeCareers(): Flow<List<CareerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(careers: List<CareerEntity>)
}
