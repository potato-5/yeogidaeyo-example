package com.hyun.sesac.home.ui.state

import com.hyun.sesac.domain.model.KakaoSearchModel
import com.hyun.sesac.domain.model.Parking

// UI 상태 (화면을 그림)
data class ParkingMapUiState(
    val isLoading: Boolean = false,
    val parkingSpots: List<Parking> = emptyList(),
    val selectedSpot: Parking? = null, // 계산된 결과를 필드로 보유

    val searchQuery: String = "",
    val searchResults: List<KakaoSearchModel> = emptyList(),
    val searchedLocation: KakaoSearchModel? = null
)

// UI 이벤트 (일회성 알림)
sealed interface UiEvent {
    data class ShowToast(val message: String) : UiEvent
}