package com.hyun.sesac.data.mapper

import com.hyun.sesac.data.entity.ParkingEntity
import com.hyun.sesac.domain.model.Parking

// 1. Entity(DB) -> Domain(앱) 변환
fun ParkingEntity.toDomainParking() = Parking(
    id = this.id,
    currentCnt = this.availableCount ?: 0,
    name = this.name,
    address = this.address,
    latitude = this.latitude,
    longitude = this.longitude,
    totalCnt = this.totalCount ?: 0,
    isBookmarked = this.isBookmarked,
    updatedTime = this.updatedTime
)

// TODO 마이페이지 즐겨찾기
// 2. Domain(앱) -> Entity(DB) 변환
fun Parking.toParkingEntity() = ParkingEntity(
    id = this.id,
    availableCount = this.currentCnt,
    name = this.name,
    address = this.address,
    latitude = this.latitude,
    longitude = this.longitude,
    totalCount = this.totalCnt,
    isBookmarked = this.isBookmarked,
    updatedTime = this.updatedTime
)