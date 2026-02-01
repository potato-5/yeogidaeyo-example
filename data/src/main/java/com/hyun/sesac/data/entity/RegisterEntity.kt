package com.hyun.sesac.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RegisterEntity.TABLE_NAME)
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
){
    companion object {
        const val TABLE_NAME = "register_parking_table"
    }
}
