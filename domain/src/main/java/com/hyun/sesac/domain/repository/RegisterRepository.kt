package com.hyun.sesac.domain.repository

import com.hyun.sesac.domain.model.RegisterParkingModel
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun getBuildingName(lat: Double, lng: Double): ProductResult<String?>
    suspend fun insertRegister(item: RegisterParkingModel): ProductResult<Unit>
    suspend fun deleteRegister(item: RegisterParkingModel): ProductResult<Unit>
    fun getRecentParking(): Flow<ProductResult<RegisterParkingModel?>>
}