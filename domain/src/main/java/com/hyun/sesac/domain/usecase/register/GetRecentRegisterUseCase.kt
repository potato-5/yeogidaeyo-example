package com.hyun.sesac.domain.usecase.register

import com.hyun.sesac.domain.model.RegisterParkingModel
import com.hyun.sesac.domain.repository.RegisterRepository
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentRegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
){
    operator fun invoke(): Flow<ProductResult<RegisterParkingModel?>>{
        return registerRepository.getRecentParking()
    }
}
