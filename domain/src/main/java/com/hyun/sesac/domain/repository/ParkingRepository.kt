package com.hyun.sesac.domain.repository

import com.hyun.sesac.domain.common.DataResourceResult
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow

interface ParkingRepository {
    fun read(): Flow<ProductResult<List<Parking>>>
    suspend fun create(parkingInfo: Parking): ProductResult<Unit>
    suspend fun update(parkingInfo: Parking): ProductResult<Unit>
    suspend fun delete(parkingID: String): ProductResult<Unit>
}