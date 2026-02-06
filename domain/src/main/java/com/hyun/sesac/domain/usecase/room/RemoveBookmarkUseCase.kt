package com.hyun.sesac.domain.usecase.room

import com.hyun.sesac.domain.repository.FireStoreParkingRepository
import com.hyun.sesac.domain.repository.RoomParkingRepository
import com.hyun.sesac.domain.result.ProductResult
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(
    private val roomParkingRepository: RoomParkingRepository
){
    suspend operator fun invoke(id: String) : ProductResult<Unit>{
        return roomParkingRepository.removeBookmark(id)
    }
}