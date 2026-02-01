package com.hyun.sesac.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyun.sesac.domain.repository.UserRepository
import com.hyun.sesac.domain.result.ProductResult
import com.hyun.sesac.shared.navigation.HomeNavigationRoute
import com.hyun.sesac.shared.navigation.LoginNavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
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
            val minSplashTime = 2000L
            val startTime = System.currentTimeMillis()

            // Room DB에서 유저 정보 확인 (first()로 현재 값만 딱 가져옴)
            val result = userRepository.currentUser()
                .filter { it !is ProductResult.Loading }
                .first()

            val isLoggedIn = if (result is ProductResult.Success) {
                // 성공했고, 그 안의 데이터가 null이 아니면 로그인 된 것!
                result.resultData != null
            } else {
                // 에러가 났거나 데이터가 없으면 로그인 안 된 것
                false
            }

            val elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime < minSplashTime) {
                delay(minSplashTime - elapsedTime)
            }

            if (isLoggedIn) {
                _startDestination.value = HomeNavigationRoute.HomeTab
            } else {
                _startDestination.value = LoginNavigationRoute.LoginScreen
            }
        }
    }
}