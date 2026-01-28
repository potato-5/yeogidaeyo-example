package com.hyun.sesac.domain.usecase.firestore

import com.hyun.sesac.domain.common.DataResourceResult
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.repository.ParkingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Inject 해줘야 함 ( parkingrepository )
class InsertParkingUseCase(val parkingRepository: ParkingRepository) {
    operator fun invoke(parking: Parking): Flow<DataResourceResult<Unit>>{
        return parkingRepository.create(parking)
    }
}