package com.hyun.sesac.data.impl

import com.hyun.sesac.data.BuildConfig
import com.hyun.sesac.data.dao.ParkingDao
import com.hyun.sesac.data.entity.ParkingEntity
import com.hyun.sesac.data.mapper.toDomainParking
import com.hyun.sesac.data.mapper.toParkingEntity
import com.hyun.sesac.data.remote.api.ParkingApiService
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val parkingDao: ParkingDao,
    private val parkingApi: ParkingApiService
): BookmarkRepository{
    // 실시간 데이터 동기화
   /* override suspend fun syncBookMarkInfo() {
        try {
            // 1. 내 즐겨찾기 목록을 로컬 DB에서 다 가져옵니다.
            val myBookmarks = parkingDao.getAllParkingEntityList()
            if (myBookmarks.isEmpty()) return

            val apiKey = BuildConfig.PUBLIC_PARKING_API_KEY
            val response = parkingApi.getCityData(apiKey)

            // 3. 응답에서 주차장 리스트(PRK_STTS) 꺼내기
            val apiList = response.parkingInfo?.rows ?: emptyList()

            // 4. 내 즐겨찾기와 비교해서 데이터 업데이트
            myBookmarks.forEach { entity ->
                // API 결과 중에 내 즐겨찾기(id)와 같은 주차장 찾기
                val targetDto = apiList.find { dto ->
                    dto.parkingCode == entity.id // PRK_CD와 Entity ID 비교
                }

                if (targetDto != null) {
                    val count = targetDto.currentCount?.toInt() ?: 0
                    parkingDao.updateParkingCount(entity.id, count)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // 에러 발생 시 그냥 무시 (기존 데이터 유지)
        }
    }*/

    // 전체 데이터를 가져와서 db에 저장하기
    override suspend fun refreshAllParkingData() {
        try{
            // 내 db에 저장된 즐겨찾기 id 목록 백업
            val bookmarkedIds = parkingDao.getBookmarkedIds()
            // api 호출
            val apiKey = BuildConfig.PUBLIC_PARKING_API_KEY
            val response = parkingApi.getCityData(apiKey)
            val apiList = response.parkingInfo?.rows ?: emptyList()

            // TODO dto.lat, long 없음
            if(apiList.isNotEmpty()){
                // dto -> entity
                val entites = apiList.map{dto ->
                    // api에서 온 id가 내 즐겨찾기 목록에 포함되어 있는지 확인
                    val isMyFavorite = bookmarkedIds.contains(dto.parkingCode)
                    // firestore의 id와 매칭되는 코드를 id로 사용
                    ParkingEntity(
                        id = dto.parkingCode,
                        name = dto.parkingName?: "정보 없음",
                        address = dto.address?: "주소 없음",
                        latitude = 0.0,
                        longitude = 0.0,
                        totalCount = dto.capacity?.toInt(),
                        availableCount = dto.currentCount?.toInt(),
                        isBookmarked = isMyFavorite,
                        updatedTime = dto.updateTime?: System.currentTimeMillis().toString()
                    )
                }
                parkingDao.insertAllParking(entites)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    // 클릭 시 db에서 꺼내주기
    override fun getParking(id: String): Flow<Parking?> {
        return parkingDao.getParkingById(id).map{ entity ->
            entity?.toDomainParking()
        }
    }

    // [Tip] 데이터가 이미 DB에 다 있으므로, insert 대신 update로 상태만 바꾸는 게 효율적
    override suspend fun addBookmark(id: String) {
        parkingDao.bookmarkParking(id)
    }

    override suspend fun removeBookmark(id: String) {
        parkingDao.unBookmarkParking(id)
    }

    override fun getBookmarkedList(): Flow<List<Parking>> {
        return parkingDao.getBookmarkedParkingList().map { list ->
            list.map { it.toDomainParking() }
        }
    }
}
