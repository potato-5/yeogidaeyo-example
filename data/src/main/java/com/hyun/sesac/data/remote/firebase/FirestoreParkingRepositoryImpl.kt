package com.hyun.sesac.data.remote.firebase

import com.hyun.sesac.data.datasource.ParkingDataSource
import com.hyun.sesac.data.di.IoDispatcher
import com.hyun.sesac.domain.common.DataResourceResult
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.repository.ParkingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FirestoreParkingRepositoryImpl @Inject constructor(
    private val dataSource: ParkingDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ParkingRepository {

    override fun read() = dataSource.read()
        .map { parkingList ->
            DataResourceResult.Success(parkingList) as DataResourceResult<List<Parking>>
        }
        .catch { e ->
            emit(DataResourceResult.Failure(e))
        }
        .onStart { emit(DataResourceResult.Loading)}
        // di에다 코루틴 inject로 주입 해야 됨
        // hiltwithmvvmpj 코드 참고
        .flowOn(ioDispatcher)

    private fun wrapCUDOperation(
        operation: suspend () -> Unit
    ): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        operation()
        emit(DataResourceResult.Success(Unit))
    }.catch{ e->
        emit(DataResourceResult.Failure(e))
    }.flowOn(ioDispatcher)

    override fun update(parkingInfo: Parking) = wrapCUDOperation {
        dataSource.update(parkingInfo)
    }

    override fun delete(parkingID: String) = wrapCUDOperation {
        dataSource.delete(parkingID)
    }

    override fun create(parkingInfo: Parking) = wrapCUDOperation {
        dataSource.create(parkingInfo)
    }
}