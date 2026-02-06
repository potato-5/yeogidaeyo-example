package com.hyun.sesac.data.remote.firebase

import com.hyun.sesac.data.datasource.ParkingDataSource
import com.hyun.sesac.data.di.IoDispatcher
import com.hyun.sesac.data.impl.utils.asProductResult
import com.hyun.sesac.data.impl.utils.safeProductResultCall
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.repository.FireStoreParkingRepository
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirestoreParkingRepositoryImpl @Inject constructor(
    private val dataSource: ParkingDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FireStoreParkingRepository {

    override fun read(): Flow<ProductResult<List<Parking>>> {
        return dataSource.read()
            .asProductResult()
            .flowOn(ioDispatcher)
    }

    override suspend fun update(parkingInfo: Parking): ProductResult<Unit> {
        return withContext(ioDispatcher) {
            safeProductResultCall {
                dataSource.update(parkingInfo)
            }
        }
    }

    override suspend fun delete(parkingID: String): ProductResult<Unit> {
        return withContext(ioDispatcher) {
            safeProductResultCall {
                dataSource.delete(parkingID)
            }
        }
    }

    override suspend fun create(parkingInfo: Parking): ProductResult<Unit> {
        return withContext(ioDispatcher) {
            safeProductResultCall {
                dataSource.create(parkingInfo)
            }
        }
    }
}