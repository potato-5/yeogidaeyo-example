package com.hyun.sesac.domain.usecase.register

import com.hyun.sesac.domain.model.RegisterParkingModel
import com.hyun.sesac.domain.repository.RegisterRepository
import com.hyun.sesac.domain.result.ProductResult
import javax.inject.Inject

// TODO Inject 해줘야 함 ( parkingrepository )
class DeleteRegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(item: RegisterParkingModel): ProductResult<Unit>{
        return registerRepository.deleteRegister(item)
    }
}