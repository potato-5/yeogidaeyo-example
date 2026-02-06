package com.hyun.sesac.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.result.ProductResult
import com.hyun.sesac.domain.usecase.auth.AuthUseCase
import com.hyun.sesac.domain.usecase.map.GetCombinedParkingListUseCase
import com.hyun.sesac.domain.usecase.room.RoomParkingUseCase
import com.hyun.sesac.mypage.ui.state.MyPageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val getCombinedParkingListUseCase: GetCombinedParkingListUseCase,
    private val roomParkingUseCase: RoomParkingUseCase
): ViewModel() {
    val uiState: StateFlow<MyPageUiState> = combine(
        authUseCase.getCurrentUserUseCase(),
        getCombinedParkingListUseCase()
    ){ userResult, roomResult ->
        val isLoading = userResult is ProductResult.Loading || roomResult is ProductResult.Loading

        var nickname = ""
        var carNum = ""

        if(userResult is ProductResult.Success){
            val user = userResult.resultData
            nickname = user?.nickname ?: ""
            carNum = user?.carNumber ?: ""
        }
        // (3) 즐겨찾기 목록 추출
        val bookmarks = if (roomResult is ProductResult.Success) {
            roomResult.resultData.filter { it.isBookmarked }
        } else {
            emptyList()
        }

        // (4) 에러 메시지 (필요 시)
        val error = if (userResult is ProductResult.RoomDBError) "유저 정보 로드 실패"
        else if (roomResult is ProductResult.RoomDBError) "즐겨찾기 로드 실패"
        else null

        // (5) 최종 상태 반환
        MyPageUiState(
            nickname = nickname,
            carNum = carNum,
            bookmarkList = bookmarks,
            isLoading = isLoading,
            errorMessage = error
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MyPageUiState(isLoading = true)
        )

    fun toggleBookmark(parking: Parking){
        viewModelScope.launch{
            if(parking.isBookmarked){
                roomParkingUseCase.removeBookmark(parking.id)
            }else{
                roomParkingUseCase.addBookmark(parking.id)
            }
        }
    }
    }
    /*val userInfo = authUseCase.getCurrentUserUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductResult.Loading
        )

    val favoriteList = roomParkingUseCase.getBookmarkList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductResult.Loading
        )*/