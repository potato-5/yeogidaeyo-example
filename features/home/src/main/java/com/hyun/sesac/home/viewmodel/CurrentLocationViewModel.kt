package com.hyun.sesac.home.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyun.sesac.domain.model.UserLocationModel
import com.hyun.sesac.domain.result.ProductResult
import com.hyun.sesac.domain.usecase.map.ObserveLocationUseCase
import com.hyun.sesac.home.service.CurrentLocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CurrentLocationViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // 서비스를 켜려면 Context가 필요해요
    observeLocationUseCase: ObserveLocationUseCase
) : ViewModel() {

    // 1. 서비스 시작 함수 (화면에서 스위치 켤 때 호출)
    val currentLocation: StateFlow<ProductResult<UserLocationModel>> =
        observeLocationUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ProductResult.Loading // 초기값도 Loading으로 변경
            )

    // [수정] UseCase 대신 직접 서비스 실행
    fun startTracking() {
        val intent = Intent(context, CurrentLocationService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    // [수정] UseCase 대신 직접 서비스 종료
    fun stopTracking() {
        val intent = Intent(context, CurrentLocationService::class.java)
        context.stopService(intent)
    }
}
