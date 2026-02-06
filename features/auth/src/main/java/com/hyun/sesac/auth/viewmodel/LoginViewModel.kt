package com.hyun.sesac.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hyun.sesac.auth.ui.state.AuthUiState
import com.hyun.sesac.domain.result.ProductResult
import com.hyun.sesac.domain.usecase.auth.AuthUseCase
import com.hyun.sesac.domain.usecase.auth.LoginGuestUseCase
import com.hyun.sesac.domain.usecase.auth.LogoutUseCase
import com.hyun.sesac.shared.ui.component.commonToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCase
): ViewModel(){
    // 입력값 상태관리
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    // 입력값 업데이트
    fun updateNickname(input: String){
        _uiState.update{ it.copy(nickname = input) }
    }

    fun updateCarNum(input: String){
        _uiState.update{ it.copy(carNum = input) }
    }

    // 게스트 로그인 버튼 클릭 시
    fun onGuestLoginClick(){
        val currentState = _uiState.value

        if(currentState.nickname.isBlank() || currentState.carNum.isBlank()){
            viewModelScope.launch {
                _event.emit(LoginEvent.ShowToast("닉네임과 차량 번호를 입력해주세요."))
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = authUseCases.loginGuest(
                nickname = currentState.nickname,
                carNumber = currentState.carNum,
                provider = "GUEST"
               // 추후 수정 provider
            )

            // 결과 처리
            when (result) {
                is ProductResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    // 성공 시 홈 화면으로 이동 신호 발송
                    _event.emit(LoginEvent.NavigateToHome)
                }
                is ProductResult.RoomDBError -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "DB 오류 발생") }
                    _event.emit(LoginEvent.ShowToast("로그인 실패: DB 오류"))
                }
                else -> {
                    // 기타 에러 처리
                    _uiState.update { it.copy(isLoading = false, errorMessage = "알 수 없는 오류") }
                    _event.emit(LoginEvent.ShowToast("로그인 실패: 다시 시도해주세요."))
                }
            }
        }
    }

    // 로그아웃
    fun onGuestLogoutClick() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // 로그아웃은 보통 실패해도 사용자를 내보내는 경우가 많지만, 정석대로 처리합니다.
            val result = authUseCases.logoutUseCase()

            when(result) {
                is ProductResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _event.emit(LoginEvent.NavigateToLogin) // 로그인 화면(초기화면)으로 이동
                }
                else -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _event.emit(LoginEvent.ShowToast("로그아웃 실패"))
                }
            }
        }
    }
}

// [추천] 이벤트를 Sealed Interface로 정의하여 관리 (파일 하단 혹은 별도 파일)
sealed interface LoginEvent {
    object NavigateToHome : LoginEvent
    object NavigateToLogin : LoginEvent
    data class ShowToast(val message: String) : LoginEvent
}