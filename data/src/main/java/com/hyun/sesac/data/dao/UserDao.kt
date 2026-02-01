package com.hyun.sesac.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hyun.sesac.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // 게스트 회원가입/로그인
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    // 로그인 여부
    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    // 게스트 삭제
    @Query("DELETE FROM ${UserEntity.TABLE_NAME}")
    suspend fun deleteUser()
}