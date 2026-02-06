package com.hyun.sesac.auth.ui.state

import android.net.Uri
import com.hyun.sesac.domain.model.RegisterParkingModel

data class AuthUiState(
    val nickname: String = "",
    val carNum: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
