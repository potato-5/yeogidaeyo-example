package com.hyun.sesac.domain.usecase.firestore

import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.repository.FireStoreParkingRepository
import com.hyun.sesac.domain.result.ProductResult
import javax.inject.Inject

class UpdateParkingUseCase @Inject constructor(
    private val fireStoreParkingRepository: FireStoreParkingRepository
) {
    suspend operator fun invoke(parking: Parking): ProductResult<Unit> {
        return fireStoreParkingRepository.update(parking)
    }
}