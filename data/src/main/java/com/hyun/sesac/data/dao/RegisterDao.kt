package com.hyun.sesac.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hyun.sesac.data.entity.RegisterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegisterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegister(item: RegisterEntity)

    @Delete
    suspend fun deleteRegister(item: RegisterEntity)

    @Query("SELECT * FROM register_parking_table ORDER BY savedAt DESC LIMIT 1")
    fun getRecentParking(): Flow<RegisterEntity?>
}