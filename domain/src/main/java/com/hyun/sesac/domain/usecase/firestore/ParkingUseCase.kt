package com.hyun.sesac.domain.usecase.firestore

import javax.inject.Inject

data class ParkingUseCase @Inject constructor(
    val getParking: GetParkingUseCase,
    val insertParking: InsertParkingUseCase,
    val updateParking: UpdateParkingUseCase,
    val deleteParkingUseCase: DeleteParkingUseCase
)