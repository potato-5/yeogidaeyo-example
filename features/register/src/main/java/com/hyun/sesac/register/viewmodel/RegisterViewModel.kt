package com.hyun.sesac.register.viewmodel

import android.net.Uri
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyun.sesac.domain.model.RegisterParkingModel
import com.hyun.sesac.domain.repository.RecognitionRepository
import com.hyun.sesac.domain.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel

// 화면의 모든 상태를 관리하는 데이터 클래스
data class RegisterUiState(
    val locationFromGps: String = "서울 성동구 아차산로 17길 48", // 기본값
    val capturedImageUri: Uri? = null,
    val parkingSpot: String = "",
    val floor: String = "2",
    val memo: String = "",
    val isLoading: Boolean = false,
    val savedItem: RegisterParkingModel? = null,
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val recognitionRepository: RecognitionRepository,
    private val registerRepository: RegisterRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    init{
        viewModelScope.launch{
            registerRepository.getRecentParking().collectLatest { item ->
              _uiState.update{
                  it.copy(
                      savedItem = item,
                      parkingSpot = item?.parkingSpot ?: it.parkingSpot,
                      floor = item?.floor ?: it.floor,
                      memo = item?.memo ?: it.memo,
                      capturedImageUri = item?.imgUri?.toUri() ?: it.capturedImageUri,
                  )
              }
            }
        }
    }

    fun saveRegister(){
        viewModelScope.launch {
            val currentState = uiState.value
            val newItem = RegisterParkingModel(
                parkingSpot = currentState.parkingSpot,
                floor = currentState.floor,
                memo = currentState.memo,
                imgUri = currentState.capturedImageUri?.toString(),
                latitude = 37.540,
                longitude = 127.07,
                savedAt = 0L
            )
            registerRepository.insertRegister(newItem)
        }
    }

    fun deleteRegister(){
        val itemToDelete = uiState.value.savedItem
        if(itemToDelete != null){
            viewModelScope.launch {
                registerRepository.deleteRegister(itemToDelete)
                _uiState.update{
                    it.copy(parkingSpot = "", memo = "", capturedImageUri = null, floor = "1")
                }
            }
        }
    }

    fun onImageCaptured(uri: Uri?){
        if(uri == null) return

        _uiState.update{
            it.copy(
                capturedImageUri = uri,
                isLoading = true
            )
        }

        viewModelScope.launch {
            val result = recognitionRepository.extractParkingText(uri.toString())

            result.onSuccess { recognitionResult ->
                _uiState.update{
                    it.copy(
                        parkingSpot = recognitionResult.zone,
                        isLoading = false
                    )
                }
            }.onFailure { error ->
                error.printStackTrace()
                _uiState.update{ it.copy(isLoading = false)}
            }
        }
    }

    fun onMemoChanged(text: String){
        _uiState.update { it.copy(memo = text) }
    }
}