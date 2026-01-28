package com.hyun.sesac.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class AuthProvider {
    GUEST,
    KAKAO,
    NAVER,
    GOOGLE
}

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val nickname: String,
    val carNum: String,
    val provider: String
)
