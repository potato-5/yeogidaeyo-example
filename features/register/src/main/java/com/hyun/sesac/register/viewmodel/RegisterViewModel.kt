package com.hyun.sesac.register.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyun.sesac.domain.model.RegisterParkingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri
import com.hyun.sesac.domain.result.ProductResult
import com.hyun.sesac.domain.usecase.map.GetKakaoLocationNameUseCase
import com.hyun.sesac.domain.usecase.map.ObserveLocationUseCase
import com.hyun.sesac.domain.usecase.register.RegisterUseCase
import com.hyun.sesac.register.ui.state.RegisterUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getKakaoLocationNameUseCase: GetKakaoLocationNameUseCase,
    private val observeLocationUseCases: ObserveLocationUseCase,
    private val registerUseCases: RegisterUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()
    private val _event = MutableSharedFlow<RegisterEvent>()
    val event = _event.asSharedFlow()

    var currentFloorLevel: Int = -1

    init {
        getRegister()
    }

    // 최근 주차 정보 가져오기
    fun getRegister() {
        _uiState.update {it.copy(isLoading = true)}

        viewModelScope.launch {
            registerUseCases.getRegister().collectLatest { result ->
                when (result) {
                    is ProductResult.Success -> {
                        val item = result.resultData
                        val restoredAddress = if (item != null) {
                            val addrResult =
                                getKakaoLocationNameUseCase(item.latitude, item.longitude)
                            if (addrResult is ProductResult.Success) addrResult.resultData else null
                        } else {
                            null
                        }

                        _uiState.update { state ->
                            state.copy(
                                savedItem = item,
                                parkingSpot = item?.parkingSpot ?: state.parkingSpot,
                                floor = item?.floor ?: state.floor,
                                memo = item?.memo ?: state.memo,
                                capturedImageUri = item?.imgUri?.toUri() ?: state.capturedImageUri,
                                locationFromGps = restoredAddress
                                    ?: (if (item == null) state.locationFromGps else ""),
                                currentLat = item?.latitude ?: state.currentLat,
                                currentLng = item?.longitude ?: state.currentLng,
                                isLoading = false // 로딩 끝
                            )
                        }
                    }

                    is ProductResult.Loading -> {
                        // 필요하면 로딩 UI 표시 ... 스피너 ..
                        // _uiState.update { it.copy(isLoading = true) }
                    }

                    is ProductResult.RoomDBError -> {
                        _uiState.update { it.copy(isLoading = false) }
                        Log.e("RegisterVM", "DB Error: ${result.exception}")
                    }

                    else -> {}
                }
            }
        }
    }

    fun saveRegister() {
        viewModelScope.launch {
            val currentState = uiState.value
            val currentTime = System.currentTimeMillis()
            val newItem = RegisterParkingModel(
                parkingSpot = currentState.parkingSpot,
                floor = currentState.floor,
                memo = currentState.memo,
                imgUri = currentState.capturedImageUri?.toString(),
                latitude = currentState.currentLat,
                longitude = currentState.currentLng,
                savedAt = currentTime
            )
            registerUseCases.insertRegister(newItem)
        }
    }

    fun deleteRegister() {
        val itemToDelete = uiState.value.savedItem
        if (itemToDelete != null) {
            viewModelScope.launch {
                registerUseCases.deleteRegister(itemToDelete)
                _uiState.update {
                    it.copy(parkingSpot = "", memo = "", capturedImageUri = null, floor = "B1")
                }
            }
        }
    }

    fun onImageCaptured(uri: Uri?) {
        if (uri == null) return

        _uiState.update {
            it.copy(
                capturedImageUri = uri,
                isLoading = true
            )
        }
        // 사진 촬영과 함께 위치 가져오기
        fetchCurrentLocation()

        viewModelScope.launch {
            val result = registerUseCases.recognitionText(uri.toString())

            when (result) {
                is ProductResult.Success -> {
                    val recognitionResult = result.resultData
                    _uiState.update {
                        it.copy(
                            parkingSpot = recognitionResult.zone,
                            isLoading = false
                        )
                    }
                }

                is ProductResult.NetworkError -> { // NetworkError 혹은 RoomDBError 등
                    _uiState.update { it.copy(isLoading = false) }
                }

                else -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun onMemoChanged(text: String) {
        _uiState.update { it.copy(memo = text) }
    }

    fun onFloorLevelChanged(isUp: Boolean) {
        if (isUp) {
            currentFloorLevel++
            if (currentFloorLevel == 0) currentFloorLevel = 1
        } else {
            currentFloorLevel--
            if (currentFloorLevel == 0) currentFloorLevel = -1
        }
        updateFloorText()
    }

    fun updateFloorText() {
        val formattedFloor = if (currentFloorLevel < 0) {
            "B${currentFloorLevel * -1}" // -1 -> "B1", -2 -> "B2"
        } else {
            "${currentFloorLevel}F"      // 1 -> "1F", 2 -> "2F"
        }

        _uiState.update {
            it.copy(floor = formattedFloor)
        }
    }

    private fun fetchCurrentLocation() {
        viewModelScope.launch {
            try {
                // 1. GPS 위치 가져오기 (상자 열기)
                val locationResult = observeLocationUseCases().first()

                if (locationResult is ProductResult.Success) {
                    val location = locationResult.resultData
                    val lat = location.latitude
                    val lng = location.longitude

                    Log.d("ParkingDebug", "좌표: $lat, $lng")

                    // 2. 카카오 API로 주소 변환 (상자 열기)
                    // withContext(Dispatchers.IO) 제거 -> Repository가 함
                    val placeNameResult = getKakaoLocationNameUseCase(lat, lng)

                    val placeName = if (placeNameResult is ProductResult.Success) {
                        placeNameResult.resultData
                    } else {
                        null
                    }

                    Log.d("ParkingDebug", "카카오 응답: $placeName")

                    if (placeName != null) {
                        _uiState.update {
                            if (it.savedItem == null) {
                                it.copy(
                                    locationFromGps = placeName,
                                    currentLat = lat,
                                    currentLng = lng
                                )
                            } else {
                                it
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

sealed interface RegisterEvent {
    data class ShowToast(val message: String) : RegisterEvent
    // 만약 저장 후 홈으로 튕겨야 한다면 NavigateToHome 도 추가 가능
}