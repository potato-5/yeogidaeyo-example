package com.hyun.sesac.domain.repository

import com.hyun.sesac.domain.model.UserLocationModel
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow

interface CurrentLocationRepository {
    fun getCurrentLocationUpdates(): Flow<ProductResult<UserLocationModel>>
    //suspend fun startLocationUpdates()
    //suspend fun stopLocationUpdates()
}