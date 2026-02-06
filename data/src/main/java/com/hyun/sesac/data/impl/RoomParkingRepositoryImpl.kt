package com.hyun.sesac.data.impl

import com.hyun.sesac.data.BuildConfig
import com.hyun.sesac.data.dao.ParkingDao
import com.hyun.sesac.data.di.IoDispatcher
import com.hyun.sesac.data.impl.utils.asProductResult
import com.hyun.sesac.data.impl.utils.safeProductResultCall
import com.hyun.sesac.data.mapper.toDomainParking
import com.hyun.sesac.data.mapper.toEntity
import com.hyun.sesac.data.remote.api.ParkingApiService
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.repository.RoomParkingRepository
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class RoomParkingRepositoryImpl @Inject constructor(
    private val parkingDao: ParkingDao,
    private val parkingApi: ParkingApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RoomParkingRepository {

    // 전체 데이터를 가져와서 db에 저장하기
    override suspend fun refreshAllParkingData(): ProductResult<Unit> {
        return withContext(ioDispatcher) {
            safeProductResultCall {
                // 내 db에 저장된 즐겨찾기 id 목록 백업
                val bookmarkedIds = parkingDao.getBookmarkedIds()
                // api 호출
                val apiKey = BuildConfig.PUBLIC_PARKING_API_KEY
                val response = parkingApi.getCityData(apiKey)
                val apiList = response.parkingInfo?.rows ?: emptyList()

                if (apiList.isNotEmpty()) {
                    val entities = apiList.map { dto ->
                        val isMyFavorite = bookmarkedIds.contains(dto.parkingCode)
                        dto.toEntity(isBookmarked = isMyFavorite)
                    }
                    parkingDao.insertAllParking(entities)
                }
            }
        }
    }

    // 클릭 시 db에서 꺼내주기
    /*override fun getParking(id: String): Flow<ProductResult<Parking?>> {
        return parkingDao.getParkingById(id)
            .map { entity -> entity?.toDomainParking() }
            .asProductResult()
    }*/


    // [Tip] 데이터가 이미 DB에 다 있으므로, insert 대신 update로 상태만 바꾸는 게 효율적
    override suspend fun addBookmark(id: String): ProductResult<Unit> {
        return safeProductResultCall {
            parkingDao.bookmarkParking(id)
        }
    }

    override suspend fun removeBookmark(id: String): ProductResult<Unit> {
        return safeProductResultCall {
            parkingDao.unBookmarkParking(id)
        }
    }

    override fun getBookmarkedList(): Flow<ProductResult<List<Parking>>> {
        return parkingDao.getBookmarkedParkingList()
            .map { list -> list.map { it.toDomainParking() } }
            .asProductResult()
    }
}



