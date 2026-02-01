package com.hyun.sesac.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ParkingEntity.TABLE_NAME)
data class ParkingEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val availableCount: Int,
    val totalCount: Int,
    val baseFee: String,
    val baseTime: String,
    val extraFee: String,
    val extraTime: String,
    val isBookmarked: Boolean = false,
    val updatedTime: String
){
    companion object {
        const val TABLE_NAME = "parking_table"
    }
}
