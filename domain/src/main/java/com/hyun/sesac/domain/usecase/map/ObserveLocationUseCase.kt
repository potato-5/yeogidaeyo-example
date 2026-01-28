package com.hyun.sesac.domain.usecase.map

import com.hyun.sesac.domain.repository.CurrentLocationRepository
import javax.inject.Inject

class ObserveLocationUseCase @Inject constructor(
    private val repository: CurrentLocationRepository
) {
    operator fun invoke() = repository.getCurrentLocationUpdates()
}