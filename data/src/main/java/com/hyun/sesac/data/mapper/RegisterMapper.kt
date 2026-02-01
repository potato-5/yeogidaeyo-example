package com.hyun.sesac.data.mapper

import com.hyun.sesac.data.entity.RegisterEntity
import com.hyun.sesac.domain.model.RegisterParkingModel

// Domain Model -> DB Entity 변환
fun RegisterParkingModel.toEntity(): RegisterEntity {
    return RegisterEntity(
        id = this.id, // insert 할 때는 0이겠지만, update/delete 때는 id가 필요함
        parkingSpot = this.parkingSpot,
        floor = this.floor,
        memo = this.memo,
        imgUri = this.imgUri,
        latitude = this.latitude,
        longitude = this.longitude,
        savedAt = this.savedAt
    )
}

// DB Entity -> Domain Model 변환
fun RegisterEntity.toDomain(): RegisterParkingModel {
    return RegisterParkingModel(
        id = this.id,
        parkingSpot = this.parkingSpot,
        floor = this.floor,
        memo = this.memo,
        imgUri = this.imgUri,
        latitude = this.latitude,
        longitude = this.longitude,
        savedAt = this.savedAt
    )
}