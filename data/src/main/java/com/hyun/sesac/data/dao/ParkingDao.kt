package com.hyun.sesac.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hyun.sesac.data.entity.ParkingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ParkingDao {
    // api 리스트 저장 및 기존 데이터 덮어쓰기
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllParking(parkingList: List<ParkingEntity>)

    // 찜하기
    @Query("UPDATE ${ParkingEntity.TABLE_NAME} SET isBookmarked = 1 WHERE id = :id")
    suspend fun bookmarkParking(id: String)

    // 찜해제 , 삭제(DELETE)가 아니라 상태 변경(UPDATE)이어야 함
    @Query("UPDATE ${ParkingEntity.TABLE_NAME} SET isBookmarked = 0 WHERE id = :id")
    suspend fun unBookmarkParking(id: String)

    // 상세 조회 id로 찾기 , marker 클릭 시 보여줄 데이터
    @Query("SELECT * FROM ${ParkingEntity.TABLE_NAME} WHERE id = :id")
    fun getParkingById(id: String): Flow<ParkingEntity?>

    // 즐겨찾기 목록 조회, 전체 데이터 중 하트 눌린 것만 가져와라 ( TODO: mypage 즐겨찾기 목록)
    @Query("SELECT * FROM ${ParkingEntity.TABLE_NAME} WHERE isBookmarked = 1")
    fun getBookmarkedParkingList(): Flow<List<ParkingEntity>>

    // 동기화 용, 현재 즐겨찾기 된 id들만 가져오기 ( repo 합칠 때 )
    @Query("SELECT id FROM ${ParkingEntity.TABLE_NAME} WHERE isBookmarked = 1")
    suspend fun getBookmarkedIds(): List<String>

    // 현재 저장된 모든 데이터 가져오기
    @Query("SELECT * FROM ${ParkingEntity.TABLE_NAME}")
    suspend fun getAllParkingSnapShot(): List<ParkingEntity>

}