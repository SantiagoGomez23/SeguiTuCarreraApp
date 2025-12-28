package com.example.seguitucarreraapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seguitucarreraapp.data.local.entity.UserSubjectStatusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSubjectStatusDao {

    @Query("SELECT * FROM user_subject_status WHERE careerId = :careerId")
    fun observeStatuses(careerId: String): Flow<List<UserSubjectStatusEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(status: UserSubjectStatusEntity)
}
