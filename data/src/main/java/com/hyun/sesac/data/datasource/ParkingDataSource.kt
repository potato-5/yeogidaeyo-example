package com.hyun.sesac.data.datasource

import com.hyun.sesac.domain.model.Parking
import kotlinx.coroutines.flow.Flow

interface ParkingDataSource {
    suspend fun delete(parkingID: String)
    suspend fun create(parking: Parking)
    suspend fun update(parking: Parking)
    fun read(): Flow<List<Parking>>
}
