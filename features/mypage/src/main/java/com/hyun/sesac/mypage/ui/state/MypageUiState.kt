package com.hyun.sesac.mypage.ui.state

import android.net.Uri
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.domain.model.RegisterParkingModel

data class MyPageUiState(
    val nickname: String = "",
    val carNum: String = "",
    val bookmarkList: List<Parking> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
