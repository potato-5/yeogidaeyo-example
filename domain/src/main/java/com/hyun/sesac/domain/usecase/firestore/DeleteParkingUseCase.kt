package com.hyun.sesac.domain.usecase.firestore

import com.hyun.sesac.domain.common.DataResourceResult
import com.hyun.sesac.domain.repository.ParkingRepository
import kotlinx.coroutines.flow.Flow

class DeleteParkingUseCase(val parkingRepository: ParkingRepository){
    operator fun invoke(parkingID: String) : Flow<DataResourceResult<Unit>>{
        return parkingRepository.delete(parkingID)
    }
}