package com.hyun.sesac.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parking_table")
data class ParkingEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val availableCount: Int?,
    val totalCount: Int?,
    val isBookmarked: Boolean = false,
    val updatedTime: String
)
