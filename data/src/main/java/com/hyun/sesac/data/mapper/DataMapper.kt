package com.hyun.sesac.data.mapper

import com.google.firebase.firestore.GeoPoint
import com.hyun.sesac.data.remote.dto.ParkingLotDTO
import com.hyun.sesac.domain.model.Parking

fun ParkingLotDTO.toDomainParking() = Parking(
    id = this.parkingCd,
    name = this.parkingNm,
    address = this.address,
    latitude = this.location?.latitude ?: 0.0,
    longitude = this.location?. longitude ?: 0.0,
    currentCnt = this.currentCnt.toIntOrNull() ?: 0,
    totalCnt = this.totalCnt.toIntOrNull() ?: 0,
    baseFee = this.baseFee,
    baseTime = this.baseTime,
    extraFee = this.extraFee,
    extraTime = this.extraTime,
    isBookmarked = this.isBookmarked
)

fun Parking.toFirestoreParkingLotDTO() = mapOf(
    "parking_cd" to this.id,
    "parking_nm" to this.name,
    "address" to this.address,
    "location" to GeoPoint(this.latitude, this.longitude),
    "currentCnt" to this.currentCnt,
    "totalCnt" to this.totalCnt,
    "baseFee" to this.baseFee,
    "baseTime" to this.baseTime,
    "extraFee" to this.extraFee,
    "extraTime" to this.extraTime,
    "isBookmarked" to this.isBookmarked
)

fun List<ParkingLotDTO>.toDomainParkingLotList() =
    this.map { it.toDomainParking() }.toList()