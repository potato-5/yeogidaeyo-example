package com.hyun.sesac.domain.usecase.firestore

import com.hyun.sesac.domain.common.DataResourceResult
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.repository.ParkingRepository
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// TODO Inject 해줘야 함 ( parkingrepository )
class InsertParkingUseCase @Inject constructor(
    private val parkingRepository: ParkingRepository
) {
    suspend operator fun invoke(parking: Parking): ProductResult<Unit>{
        return parkingRepository.create(parking)
    }
}