package com.hyun.sesac.domain.usecase.room

import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.repository.FireStoreParkingRepository
import com.hyun.sesac.domain.repository.RoomParkingRepository
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkedListUseCase @Inject constructor(
    private val roomParkingRepository: RoomParkingRepository
){
    operator fun invoke() : Flow<ProductResult<List<Parking>>>{
        return roomParkingRepository.getBookmarkedList()
    }
}