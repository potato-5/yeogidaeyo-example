package com.hyun.sesac.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyun.sesac.domain.usecase.auth.LoginGuestUseCase
import com.hyun.sesac.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val guestUseCase: LoginGuestUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel(){
    // 입력값 상태관리
    private val _nickname = MutableStateFlow("")
    val nickname = _nickname.asStateFlow()

    private val _carNum = MutableStateFlow("")
    val carNum = _carNum.asStateFlow()

    // 로그인 성공 이벤트
    private val _loginEvent = MutableSharedFlow<Unit>()
    val loginEvent = _loginEvent.asSharedFlow()

    // 로그아웃 성공 이벤트
    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent = _logoutEvent.asSharedFlow()

    // 입력값 업데이트
    fun updateNickname(input: String){
        _nickname.value = input
    }

    fun updateCarNum(input: String){
        _carNum.value = input
    }

    // 게스트 로그인 버튼 클릭 시
    fun onGuestLoginClick(){
        val currentNick = _nickname.value
        val currentCar = _carNum.value

        if(currentNick.isBlank() || currentCar.isBlank()){
            return
        }

        viewModelScope.launch {
           guestUseCase(
                nickname = currentNick,
                carNumber = currentCar,
                provider = "GUEST"
               // 추후 수정 provider
            )
            // 저장이 끝나면 ui에게 성공 신호 보냄
            _loginEvent.emit(Unit)
        }
    }

    // TODO 다른것도 다 try-catch 감싸기?
    fun onGuestLogoutClick(){
        viewModelScope.launch{
            runCatching {
            logoutUseCase()
            }.onSuccess {
            _logoutEvent.emit(Unit)
            }.onFailure { error ->
                Log.e("Logout", "logout failed", error)
            }
        }
    }
}