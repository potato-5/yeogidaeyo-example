package com.hyun.sesac.domain.repository

import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow

interface RoomParkingRepository {
    // 전체 데이터를 가져와서 db에 넣는 로직
    suspend fun refreshAllParkingData(): ProductResult<Unit>

    // parking id 값 가져오기
    //fun getParking(id: String): Flow<ProductResult<Parking?>>

    // 즐겨찾기 추가/삭제
    suspend fun addBookmark(id: String): ProductResult<Unit>
    suspend fun removeBookmark(id: String): ProductResult<Unit>

    // 내 즐겨찾기 목록 보기
    fun getBookmarkedList(): Flow<ProductResult<List<Parking>>>
}
