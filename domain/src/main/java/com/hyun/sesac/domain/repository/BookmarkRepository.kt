package com.hyun.sesac.domain.repository

import com.hyun.sesac.domain.model.Parking
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    // 전체 데이터를 가져와서 db에 넣는 로직
    suspend fun refreshAllParkingData(){}

    // parking id 값 가져오기
    fun getParking(id: String): Flow<Parking?>

    // 즐겨찾기 추가/삭제
    suspend fun addBookmark(id: String)
    suspend fun removeBookmark(id: String)

    // 내 즐겨찾기 목록 보기
    fun getBookmarkedList(): Flow<List<Parking>>
}
