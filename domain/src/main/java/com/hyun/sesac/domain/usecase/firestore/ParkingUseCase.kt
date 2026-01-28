package com.hyun.sesac.domain.usecase.firestore

data class ParkingUseCase(
    val getParking: GetParkingUseCase,
    val insertParking: InsertParkingUseCase,
    val updateParking: UpdateParkingUseCase,
    val deleteParkingUseCase: DeleteParkingUseCase
)