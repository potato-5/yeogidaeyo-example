package com.hyun.sesac.domain.usecase.register

import com.hyun.sesac.domain.usecase.firestore.DeleteParkingUseCase
import com.hyun.sesac.domain.usecase.firestore.GetParkingUseCase
import com.hyun.sesac.domain.usecase.firestore.InsertParkingUseCase
import com.hyun.sesac.domain.usecase.firestore.UpdateParkingUseCase
import com.hyun.sesac.domain.usecase.map.GetKakaoLocationNameUseCase
import javax.inject.Inject

data class RegisterUseCase @Inject constructor(
    val getRegister: GetRecentRegisterUseCase,
    val insertRegister: InsertRegisterUseCase,
    val deleteRegister: DeleteRegisterUseCase,
    val recognitionText: RecognitionUseCase
    //val getKakaoLocation: GetKakaoLocationNameUseCase,
)