package com.hyun.sesac.data.impl

import com.hyun.sesac.data.dao.RegisterDao
import com.hyun.sesac.data.entity.RegisterEntity
import com.hyun.sesac.domain.model.RegisterParkingModel
import com.hyun.sesac.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDao: RegisterDao
): RegisterRepository {
    override suspend fun insertRegister(item: RegisterParkingModel) {
        val entity = RegisterEntity(
            id = item.id,
            parkingSpot = item.parkingSpot,
            floor = item.floor,
            memo = item.memo,
            imgUri = item.imgUri,
            latitude = item.latitude,
            longitude = item.longitude,
            savedAt = item.savedAt
        )
        registerDao.insertRegister(entity)
    }

    override suspend fun deleteRegister(item: RegisterParkingModel) {
        val entity = RegisterEntity(
            id = item.id,
            parkingSpot = item.parkingSpot,
            floor = item.floor,
            memo = item.memo,
            imgUri = item.imgUri,
            latitude = item.latitude,
            longitude = item.longitude,
            savedAt = item.savedAt
        )
        registerDao.deleteRegister(entity)
    }

    override fun getRecentParking(): Flow<RegisterParkingModel?> {
        return registerDao.getRecentParking().map { entity ->
            entity.let{
                RegisterParkingModel(
                    id = it?.id ?: 0,
                    parkingSpot = it?.parkingSpot ?: "",
                    floor = it?.floor ?: "1",
                    memo = it?.memo ?: "",
                    imgUri = it?.imgUri,
                    latitude = it?.latitude ?: 0.0,
                    longitude = it?.longitude ?: 0.0,
                    savedAt = it?.savedAt ?: 0,
                )
            }
        }
    }
}