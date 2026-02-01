package com.hyun.sesac.register.ui.state

import android.net.Uri
import com.hyun.sesac.domain.model.RegisterParkingModel

data class RegisterUiState(
    val locationFromGps: String = "",
    val currentLat: Double = 0.0,
    val currentLng: Double = 0.0,
    val capturedImageUri: Uri? = null,
    val parkingSpot: String = "",
    val floor: String = "B1",
    val memo: String = "",
    val isLoading: Boolean = false,
    val savedItem: RegisterParkingModel? = null,
)
