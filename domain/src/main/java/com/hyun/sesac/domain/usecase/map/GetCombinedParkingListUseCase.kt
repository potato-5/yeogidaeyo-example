package com.hyun.sesac.domain.usecase.map

import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.result.ProductResult
import com.hyun.sesac.domain.usecase.firestore.ParkingUseCase
import com.hyun.sesac.domain.usecase.room.RoomParkingUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCombinedParkingListUseCase @Inject constructor(
    private val parkingUseCase: ParkingUseCase,
    private val roomParkingUseCase: RoomParkingUseCase,
) {
    operator fun invoke(): Flow<ProductResult<List<Parking>>> {
        return combine(
            parkingUseCase.getParking(),            // Firestore Flow
            roomParkingUseCase.getBookmarkList()  // Room Flow
        ) { firestoreResult, localResult ->

            if (firestoreResult is ProductResult.Loading || localResult is ProductResult.Loading) {
                return@combine ProductResult.Loading
            }

            val baseList = if (firestoreResult is ProductResult.Success) firestoreResult.resultData else emptyList()
            val overlayList = if (localResult is ProductResult.Success) localResult.resultData else emptyList()

            val overlayMap = overlayList.associateBy { it.id }

            val mergedList = baseList.map { original ->
                val overlay = overlayMap[original.id]
                if (overlay != null) {
                    original.copy(
                        isBookmarked = true,
                        //currentCnt = overlay.currentCnt
                    )
                } else {
                    original.copy(isBookmarked = false)
                }
            }

            ProductResult.Success(mergedList)
        }
    }
}