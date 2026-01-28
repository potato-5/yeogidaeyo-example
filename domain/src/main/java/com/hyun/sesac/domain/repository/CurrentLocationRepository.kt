package com.hyun.sesac.domain.repository

import com.hyun.sesac.domain.model.UserLocationModel
import kotlinx.coroutines.flow.Flow

interface CurrentLocationRepository {
    fun getCurrentLocationUpdates(): Flow<UserLocationModel>
    suspend fun startLocationUpdates()
    suspend fun stopLocationUpdates()
}