package com.hyun.sesac.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "register_parking_table")
data class RegisterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val parkingSpot: String,
    val floor: String,
    val memo: String,
    val imgUri: String?,
    val latitude: Double,
    val longitude: Double,
    val savedAt: Long
)
