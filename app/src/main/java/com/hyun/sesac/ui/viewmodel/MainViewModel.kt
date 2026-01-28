package com.hyun.sesac.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyun.sesac.domain.repository.UserRepository
import com.hyun.sesac.shared.navigation.HomeNavigationRoute
import com.hyun.sesac.shared.navigation.LoginNavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel(){
    // 앱의 시작 상태 (로딩중 / 로그인됨 / 로그인안됨)
    private val _startDestination = MutableStateFlow<Any?>(null) // null이면 로딩중(Splash)
    val startDestination = _startDestination.asStateFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            delay(2000)
            // Room DB에서 유저 정보 확인 (first()로 현재 값만 딱 가져옴)
            val user = userRepository.currentUser().first()

            if (user != null) {
                // 로그인 정보 있음 -> 홈으로
                _startDestination.value = HomeNavigationRoute.HomeTab
            } else {
                // 로그인 정보 없음 -> 로그인 화면으로
                // (import 주의: Step 1에서 만든 Route)
                _startDestination.value = LoginNavigationRoute.LoginScreen
            }
        }
    }
}