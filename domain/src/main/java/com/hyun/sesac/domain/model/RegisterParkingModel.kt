package com.hyun.sesac.domain.model

data class RegisterParkingModel(
    val id: Long = 0,
    val parkingSpot: String,
    val floor: String,
    val memo: String,
    val imgUri: String?,
    val latitude: Double,
    val longitude: Double,
    val savedAt: Long
)
