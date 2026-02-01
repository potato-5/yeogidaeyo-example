package com.hyun.sesac.domain.usecase.firestore

import com.hyun.sesac.domain.common.DataResourceResult
import com.hyun.sesac.domain.repository.ParkingRepository
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteParkingUseCase @Inject constructor(
    private val parkingRepository: ParkingRepository
){
    suspend operator fun invoke(parkingID: String) : ProductResult<Unit>{
        return parkingRepository.delete(parkingID)
    }
}