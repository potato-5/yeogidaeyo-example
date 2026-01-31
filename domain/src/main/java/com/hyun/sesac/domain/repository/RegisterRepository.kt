package com.hyun.sesac.domain.repository

import com.hyun.sesac.domain.model.RegisterParkingModel
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun insertRegister(item: RegisterParkingModel)
    suspend fun deleteRegister(item: RegisterParkingModel)
    fun getRecentParking(): Flow<RegisterParkingModel?>
}