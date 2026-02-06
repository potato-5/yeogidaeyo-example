package com.hyun.sesac.domain.usecase.firestore

import com.hyun.sesac.domain.repository.FireStoreParkingRepository
import com.hyun.sesac.domain.result.ProductResult
import javax.inject.Inject

class DeleteParkingUseCase @Inject constructor(
    private val fireStoreParkingRepository: FireStoreParkingRepository
){
    suspend operator fun invoke(parkingID: String) : ProductResult<Unit>{
        return fireStoreParkingRepository.delete(parkingID)
    }
}