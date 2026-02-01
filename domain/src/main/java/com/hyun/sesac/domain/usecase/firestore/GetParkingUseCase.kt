package com.hyun.sesac.domain.usecase.firestore

import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.repository.ParkingRepository
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// todo 필요하다면 여기서 유효성 체크 해주기 !
class GetParkingUseCase @Inject constructor(
    private val parkingRepository: ParkingRepository
) {
    operator fun invoke(): Flow<ProductResult<List<Parking>>> {
        return parkingRepository.read()
    }
}