package com.hyun.sesac.data.mapper

import com.hyun.sesac.data.entity.ParkingEntity
import com.hyun.sesac.data.remote.dto.ParkingItem
import com.hyun.sesac.data.remote.dto.ParkingInfoDto
import com.hyun.sesac.domain.model.Parking

// 1. Entity(DB) -> Domain(앱) 변환
fun ParkingEntity.toDomainParking() = Parking(
    id = this.id,
    currentCnt = this.availableCount,
    name = this.name,
    address = this.address,
    latitude = this.latitude,
    longitude = this.longitude,
    totalCnt = this.totalCount,
    baseFee = this.baseFee,
    baseTime = this.baseTime,
    extraFee = this.extraFee,
    extraTime = this.extraTime,
    isBookmarked = this.isBookmarked,
    updatedTime = this.updatedTime
)

// 실시간 API 데이터(ParkingItem) -> DB 데이터(Entity) 변환
fun ParkingItem.toEntity(isBookmarked: Boolean): ParkingEntity {
    return ParkingEntity(
        id = this.parkingCode, // DTO 필드명: parkingCode
        name = this.parkingName ?: "정보 없음",
        address = this.address ?: "주소 없음",

        // TODO: 실시간 API에는 좌표(lat/lng)가 없으므로 일단 0.0 처리
        // (나중에 주소로 좌표 찾는 로직이 필요할 수 있음)
        latitude = 0.0,
        longitude = 0.0,

        // DTO는 Double? 이므로 toInt() 변환 필요
        totalCount = this.capacity?.toInt() ?: 0,
        availableCount = this.currentCount?.toInt() ?: 0,

        // API에 요금 정보가 없다면 기본값 처리
        baseFee = "정보 없음",
        baseTime = "정보 없음",
        extraFee = "정보 없음",
        extraTime = "정보 없음",

        isBookmarked = isBookmarked,
        updatedTime = this.updateTime ?: System.currentTimeMillis().toString()
    )
}

// 2. Domain(앱) -> Entity(DB) 변환
fun Parking.toParkingEntity() = ParkingEntity(
    id = this.id,
    availableCount = this.currentCnt,
    name = this.name,
    address = this.address,
    latitude = this.latitude,
    longitude = this.longitude,
    totalCount = this.totalCnt,
    baseFee = this.baseFee,
    baseTime = this.baseTime,
    extraFee = this.extraFee,
    extraTime = this.extraTime,
    isBookmarked = this.isBookmarked,
    updatedTime = this.updatedTime
)
