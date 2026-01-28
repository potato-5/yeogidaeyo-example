package com.hyun.sesac.data.mapper

import com.hyun.sesac.data.entity.ParkingEntity
import com.hyun.sesac.data.entity.UserEntity
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.model.UserInfo

// 1. Entity(DB) -> Domain(앱) 변환
fun UserEntity.toDomainUser() = UserInfo(
    id = this.id,
    nickname = this.nickname,
    carNumber = this.carNum,
    provider = this.provider,
)

// TODO 마이페이지 즐겨찾기
// 2. Domain(앱) -> Entity(DB) 변환
fun UserInfo.toUserEntity() = UserEntity(
    id = this.id,
    nickname = this.nickname,
    carNum = this.carNumber,
    provider = this.provider,
)