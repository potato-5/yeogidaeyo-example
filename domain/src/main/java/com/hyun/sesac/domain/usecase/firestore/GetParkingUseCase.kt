package com.hyun.sesac.domain.usecase.firestore

import com.hyun.sesac.domain.repository.ParkingRepository
import javax.inject.Inject

// 필요하다면 여기서 유효성 체크 해주기 !
class GetParkingUseCase @Inject constructor(
    val parkingRepository: ParkingRepository
){
    operator fun invoke() = parkingRepository.read()
}